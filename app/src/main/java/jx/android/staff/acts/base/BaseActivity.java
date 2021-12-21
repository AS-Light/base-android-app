package jx.android.staff.acts.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.aslight.titlebar.utils.KeyboardConflictCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import jx.android.staff.R;
import jx.android.staff.app.AppLog;
import jx.android.staff.app.AppManager;
import jx.android.staff.dialog.WaitingDialog;
import jx.android.staff.utils.DateUtils;
import jx.android.staff.utils.FastDoubleClickUtil;

/**
 * 基础Activity，基于FragmentActivity、DataBinding、lifecycle
 */
public abstract class BaseActivity<SV extends ViewDataBinding> extends FragmentActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 0;

    protected SV mViewBinding;
    protected boolean couldExitApp = false;

    private boolean mBackKeyPressed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        long startTime = DateUtils.getNowTimeZone();

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
            AppLog.e("onCreate fixOrientation when Oreo, result = " + result);
        }

        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        requestPermissions();

        // 子类赋予布局 setContentView
        setContentView(getLayoutRes());

        // 初始化View，主要是ListView和Adapter相关内容
        initViews();
        // 初始化DataBinding相关的ViewModel、Handler、Delegate内容
        initBinding();
        // 初始化数据和网络接口调用
        initData();
        // 初始化网络接口调用后返回数据的观察者，及其处理方式
        initObserver();

        long endTime = DateUtils.getNowTimeZone();
        AppLog.e(getActivityName() + " on create with time : " + (endTime - startTime));
    }

    @Override
    public void setContentView(int layoutResID) {
        // DataBinding拦截生成View
        mViewBinding = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);
        getWindow().setContentView(mViewBinding.getRoot());
    }

    @Override
    protected void onResume() {
        long startTime = DateUtils.getNowTimeZone();

        super.onResume();
        getWindow().setBackgroundDrawableResource(R.color.white);

        long endTime = DateUtils.getNowTimeZone();
        AppLog.e(getActivityName() + " on resume with time : " + (endTime - startTime));
    }

    @Override
    protected void onPause() {
        WaitingDialog.stopLoading();
        super.onPause();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        KeyboardConflictCompat.assistWindow(getWindow());
    }

    @Override
    protected void onDestroy() {
        mViewBinding.unbind();
        super.onDestroy();

        AppManager.getAppManager().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (WaitingDialog.isLoading()) {
            WaitingDialog.stopLoading();
        } else if (couldExitApp) {
            if (!mBackKeyPressed) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mBackKeyPressed = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mBackKeyPressed = false;
                    }
                }, 2000);
            } else {
                AppManager.getAppManager().AppExit();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 注入Activity布局文件
     * 返回当前页面布局文件ID
     */
    public abstract int getLayoutRes();

    /**
     * 初始化View，主要是ListView和Adapter相关内容
     */
    public abstract void initViews();

    /**
     * 初始化DataBinding相关的ViewModel、Handler、Delegate内容
     */
    public abstract void initBinding();

    /**
     * 初始化数据和网络接口调用
     */
    public abstract void initData();

    /**
     * 初始化网络接口调用后返回数据的观察者，及其处理方式
     */
    public abstract void initObserver();

    /**
     * 返回（中文）Activity名称
     */
    public abstract String getActivityName();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (FastDoubleClickUtil.isFastDoubleClick()) {
                return false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            AppLog.e("avoid calling setRequestedOrientation when Oreo.");
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            @SuppressLint("PrivateApi") int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo activityInfo = (ActivityInfo) field.get(this);
            Objects.requireNonNull(activityInfo).screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final long WAIT_DIALOG_COUNT_DOWN = 5;
    private int waitDialogCount = 0;
    private CountDownTimer waitDialogCountDownTimer;

    /**
     * 显示Loading对话框
     * Loading对话框在最后一次打开（WAIT_DIALOG_COUNT_DOWN秒）后，关闭对话框
     */
    public void showWaitDialog() {
        try {
            WaitingDialog.showLoading(this);
            waitDialogCount++;

            // 关闭倒计时，开启新的倒计时
            if (waitDialogCountDownTimer != null) {
                waitDialogCountDownTimer.cancel();
            }
            waitDialogCountDownTimer = new CountDownTimer(WAIT_DIALOG_COUNT_DOWN * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    // TODO: 没事
                }

                @Override
                public void onFinish() {
                    // 倒计时后强制关闭Loading对话框
                    waitDialogCount = 1;
                    hideWaitDialog();
                }
            };
            waitDialogCountDownTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏Loading对话框
     */
    public void hideWaitDialog() {
        try {
            waitDialogCount = waitDialogCount > 0 ? waitDialogCount - 1 : waitDialogCount;
            if (waitDialogCount == 0) {
                WaitingDialog.stopLoading();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * （在不隐藏和显示Loading对话框的情况下）改变Loading对话框的文字内容
     */
    public void changeWaitMessage(String message) {
        try {
            WaitingDialog.changeMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求权限的公共方法
     */
    public void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CALL_PHONE);
            }
            if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.BLUETOOTH);
            }
            if (permissions.size() > 0) {
                int permissionsSize = permissions.size();
                requestPermissions(permissions.toArray(new String[permissionsSize]), PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    /**
     * 隐藏按键
     */
    public void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
