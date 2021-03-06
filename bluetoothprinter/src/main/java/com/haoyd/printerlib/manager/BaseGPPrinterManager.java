package com.haoyd.printerlib.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.gprinter.aidl.GpService;
import com.gprinter.command.GpCom;
import com.gprinter.io.GpDevice;
import com.gprinter.io.PortParameters;
import com.gprinter.service.GpPrintService;
import com.haoyd.printerlib.GPPrinterConfig;
import com.haoyd.printerlib.dao.GPPrinterDao;
import com.haoyd.printerlib.entities.BluetoothDeviceInfo;
import com.haoyd.printerlib.liseners.OnPrintResultListener;
import com.haoyd.printerlib.receivers.PrinterBroadcastReceiver;
import com.haoyd.printerlib.utils.ActivityUtil;
import com.haoyd.printerlib.utils.BluetoothUtil;
import com.haoyd.printerlib.views.PrinterStatusDialog;

import java.util.Timer;
import java.util.TimerTask;

import static com.haoyd.printerlib.GPPrinterConstant.DEFAULT_PRINTER_ID;
import static com.haoyd.printerlib.GPPrinterConstant.MAIN_QUERY_PRINTER_STATUS;

/**
 * 该类集成了打印机操作的一些基本功能
 * 1、连接、断开打印机
 * 2、判断是否已经连接
 * 3、获取打印状态
 * 4、设置打印结果监听
 * 5、打印小票
 */
public class BaseGPPrinterManager {

    protected Activity mActivity;
    private PrinterServiceConnection conn = null;
    private GpService mGpService = null;
    private PrinterBroadcastReceiver printerBroadcastReceiver = null;
    private Timer timer = null;
    private PrinterStatusDialog.Builder printerStatusDialog;
    private OnPrintResultListener onPrintResultListener;
    private boolean connectHistory = false;

    public BaseGPPrinterManager(Activity mActivity) {
        this.mActivity = mActivity;
        printerBroadcastReceiver = new PrinterBroadcastReceiver();
        printerStatusDialog = new PrinterStatusDialog.Builder(mActivity);
    }

    /**
     * 绑定打印服务
     */
    public void bindService() {
        conn = new PrinterServiceConnection();
        Intent intent = new Intent(mActivity, GpPrintService.class);
        mActivity.bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService

        // 注册实时状态查询广播
        mActivity.registerReceiver(printerBroadcastReceiver, new IntentFilter(GpCom.ACTION_DEVICE_REAL_STATUS));
        // 票据模式下，可注册该广播，在需要打印内容的最后加入addQueryPrinterStatus()，在打印完成后会接收到
        mActivity.registerReceiver(printerBroadcastReceiver, new IntentFilter(GpCom.ACTION_RECEIPT_RESPONSE));
    }

    /**
     * 解绑打印服务
     */
    public void unbindService() {
        if (conn != null) {
            mActivity.unbindService(conn);
        }
        mActivity.unregisterReceiver(printerBroadcastReceiver);
        clearTask();
    }

    /**
     * 是否连接到打印机
     * @return
     */
    public boolean isConnecting() {
        try {
            if (mGpService != null && mGpService.getPrinterConnectStatus(0) == GpDevice.STATE_CONNECTED) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 打印打印机当前状态
     */
    public void queryPrinterStatus() {
        try {
            mGpService.queryPrinterStatus(DEFAULT_PRINTER_ID, 1500, MAIN_QUERY_PRINTER_STATUS);
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * 设置打印状态监听
     * @param listener
     */
    public void setOnPrinterConnResultListener(final OnPrintResultListener listener) {
        this.onPrintResultListener = listener;
        if (printerBroadcastReceiver != null && listener != null) {
            printerBroadcastReceiver.setOnPrintResultListener(new OnPrintResultListener() {
                @Override
                public void onPrintSucc() {
                    clearTask();

                    // 如果该Activity非当前Activity只不进行回调处理
                    if (!ActivityUtil.isActivityTop(mActivity)) {
                        return;
                    }

                    listener.onPrintSucc();
                    if (GPPrinterConfig.showPrintStateDialog) {
                        printerStatusDialog.setModeSuccess();
                    }
                }

                @Override
                public void onPrintError(String error) {
                    clearTask();

                    // 如果该Activity非当前Activity只不进行回调处理
                    if (!ActivityUtil.isActivityTop(mActivity)) {
                        return;
                    }

                    listener.onPrintError(error);

                    if (error.contains("缺纸") && GPPrinterConfig.alertLackOfPager) {
                        if (printerStatusDialog.getMode() == PrinterStatusDialog.MODE_PRINTING) {
                            printerStatusDialog.cancel();
                        }

                        new AlertDialog.Builder(mActivity)
                                .setTitle("提示")
                                .setMessage("打印机没有纸了，请先安装打印纸")
                                .setPositiveButton("确定", null)
                                .show();
                    } else if (!error.contains("缺纸") && GPPrinterConfig.showPrintStateDialog) {
                        printerStatusDialog.setModeError();
                    }
                }
            });
        }
    }

    /**
     * 指定要连接的打印机
     * @param info
     * @return true：连接成功   false：连接失败
     */
    public void connectToPrinter(BluetoothDeviceInfo info) {
        connect(info, true);
        HistoryConnRecManager.setHistoryConnct(false);
    }

    /**
     * 断开与打印机的连接
     */
    public void disConnectToPrinter() {
        try {
            if (mGpService != null && mGpService.getPrinterConnectStatus(0) == GpDevice.STATE_CONNECTED) {
                mGpService.closePort(DEFAULT_PRINTER_ID);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 打印小票信息
     * @param data
     * @return
     */
    public boolean printTicket(String data) {
        if (TextUtils.isEmpty(data)) {
            callbackPrintErrorMsg("数据为空，不可打印~");
            return false;
        }

        if (!BluetoothUtil.isSupportBluetooth()) {
            callbackPrintErrorMsg("设备不支持蓝牙功能~");
            return false;
        }

        // 蓝牙未开启
        if (BluetoothUtil.isClosed()) {
            callbackPrintErrorMsg("蓝牙未开启");
            BluetoothUtil.forceOpenBluetooth();
            return false;
        }

        if (!isConnecting()) {
            callbackPrintErrorMsg("打印机未连接");
            return false;
        }

        int rs;
        try {
            rs = mGpService.sendEscCommand(DEFAULT_PRINTER_ID, data);
            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
            if (r != GpCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(mActivity.getApplicationContext(), GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

        if (GPPrinterConfig.showPrintStateDialog) {
            printerStatusDialog.setModePrint();
        }

        if (GPPrinterConfig.checkErrorWhenPrinting) {
            timer = new Timer();
            timer.schedule(new ListenPrinterTask(), 0, 1000);
        }

        return true;
    }

    /**
     * 连接到历史打印机
     */
    public void connToHistoryDevice() {
        connectHistory = true;

        if (!BluetoothUtil.isOpening()) {
            return;
        }

        BluetoothDeviceInfo bluetoothDeviceInfo = GPPrinterDao.getInstance(mActivity).getBluetoothDeviceInfo();
        if (bluetoothDeviceInfo == null) {
            return;
        }

        if (mGpService == null) {
            return;
        }

        connect(bluetoothDeviceInfo, false);
        HistoryConnRecManager.setHistoryConnct(true);
    }

    private void toast(String msg) {
        if (mActivity == null || TextUtils.isEmpty(msg)) {
            return;
        }

        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    private void clearTask() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    /**
     * 回调打印错误信息
     * @param error
     */
    private void callbackPrintErrorMsg(String error) {
        if (onPrintResultListener == null) {
            return;
        }

        onPrintResultListener.onPrintError(error);
    }

    /**
     * 连接打印机
     * @param info      // 打印机信息
     * @param showErrorToast    // 当发生错误时是否toast error信息
     */
    private void connect(BluetoothDeviceInfo info, boolean showErrorToast) {
        if (info == null) {
            if (showErrorToast) {
                toast("数据错误");
            }
            return;
        }

        if (!BluetoothUtil.isOpening()) {
            if (showErrorToast) {
                toast("请先开启蓝牙");
            }
            return;
        }

        int connStatus = 0;

        try {
            // 如果打印机已经连接了，就不要再重复连接了
            if (mGpService != null && mGpService.getPrinterConnectStatus(0) == GpDevice.STATE_CONNECTED) {
                return;
            }

            connStatus = mGpService.openPort(DEFAULT_PRINTER_ID, PortParameters.BLUETOOTH, info.address, 0);
        } catch (Exception e) {
            if (showErrorToast) {
                toast("连接失败");
            }
            return;
        }

        GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[connStatus];

        if (r != GpCom.ERROR_CODE.SUCCESS) {
            if (showErrorToast) {
                toast(GpCom.getErrorText(r));
            }
            return;
        }

        info.isConnected = true;
        GPPrinterDao.getInstance(mActivity).setBluetoothDeviceInfo(info);
        GPPrinterConnectingManager.getInstance().setConnectingDeviceInfo(info);
    }

    class PrinterServiceConnection implements ServiceConnection {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mGpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGpService = GpService.Stub.asInterface(service);

            if (connectHistory && !isConnecting()) {
                connToHistoryDevice();
            }
        }
    }

    class ListenPrinterTask extends TimerTask {
        @Override
        public void run() {
            queryPrinterStatus();
        }
    }
}
