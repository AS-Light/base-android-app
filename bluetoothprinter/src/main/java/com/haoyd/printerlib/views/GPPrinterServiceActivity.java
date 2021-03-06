package com.haoyd.printerlib.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.haoyd.printerlib.GPPrinterConfig;
import com.haoyd.printerlib.dao.GPPrinterDao;
import com.haoyd.printerlib.liseners.OnPrintResultListener;
import com.haoyd.printerlib.manager.GPPrinterManager;
import com.haoyd.printerlib.manager.HistoryConnRecManager;
import com.haoyd.printerlib.receivers.PrinterConnReceiverManager;

/**
 * 该类主要有以下几个作用：
 * 1、初始化打印管理类
 * 2、监听连接成功与否
 * 3、监听打印成功与否
 */
public class GPPrinterServiceActivity extends AppCompatActivity implements OnPrintResultListener, PrinterConnReceiverManager.OnConnResultListener {

    protected GPPrinterManager printerManager;
    private PrinterConnReceiverManager connReceiverManager;

    /**
     * Printer Config
     */
    protected boolean disconnectWhenFinish = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        printerManager = new GPPrinterManager(this);
        connReceiverManager = new PrinterConnReceiverManager(this);

        processServiceBindLogic(true);

        connReceiverManager.setResultListener(this);

        if (GPPrinterConfig.autoConnectHistoryPrinter) {
            connectHistoryPrinter();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        processServiceBindLogic(false);
    }

    /**
     * 绑定或解绑打印服务
     * @param isRegist  true：绑定   false：解绑
     */
    private void processServiceBindLogic(boolean isRegist) {
        if (isRegist) {
            // 绑定服务
            printerManager.bindService();
            printerManager.setOnPrinterConnResultListener(this);
        } else {
            // 解绑服务
            if (disconnectWhenFinish) {
                printerManager.disConnectToPrinter();
            }
            printerManager.unbindService();
            connReceiverManager.unregist();
            printerManager = null;
        }
    }


    /**
     * 打印成功
     */
    @Override
    public void onPrintSucc() {

    }

    /**
     * 打印失败
     * @param error 失败原因
     */
    @Override
    public void onPrintError(String error) {
    }

    /**
     * 连接成功
     */
    @Override
    public void onConnSuccess() {

    }

    /**
     * 连接失败
     * @param error 失败原因
     */
    @Override
    public void onConnFail(String error) {
        if (!HistoryConnRecManager.isHistoryConnct()) {
            toast(error);
        }
    }

    /**
     * 断开连接
     */
    @Override
    public void onDisconnect() {

    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void connectHistoryPrinter() {
        if (GPPrinterDao.getInstance(this).hasHistoryPrinter()) {
            printerManager.connToHistoryDevice();
        }
    }
}
