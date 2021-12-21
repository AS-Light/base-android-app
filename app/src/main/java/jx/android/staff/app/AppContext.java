package jx.android.staff.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;

import jx.android.staff.acts.WelcomeActivity;

public class AppContext extends Application {

    // AppContext单例
    private static AppContext mAppContextInstance;
    private static Resources mResourceInstance;

    /**
     * 获得当前app运行的AppContext
     *
     * @return 单例
     */
    public static AppContext getInstance() {
        return mAppContextInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContextInstance = this;
        mResourceInstance = mAppContextInstance.getResources();
        init();
    }

    /**
     * 返回应用程序Resources单例
     */
    public static Resources resources() {
        return mResourceInstance;
    }

    /**
     * 1、初始化网络请求
     * 2、初始化Log控制器
     * 3、初始化Bitmap缓存地址
     */
    private void init() {
        // 初始化cache
        initAppCache();
        // 初始化是否打印log
        initLog();
        // 初始化一个独立运行的App服务
        initAppService();
        // 初始化微信SDK
        initWX();
        // UtilCode初始化
        initUtils();
    }

    private void initAppService() {
        Intent intent = new Intent(this, AppService.class);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }*/
    }

    /**
     * 初始化cache
     */
    private void initAppCache() {
        AppCache.getInstance().init(this);
    }

    /**
     * 初始化微信SDK
     */
    private void initWX() {

    }

    /**
     * 初始化android utils
     */
    private void initUtils() {
        Utils.init(getInstance());
    }

    /**
     * 初始化app log
     */
    private void initLog() {
        AppLog.init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * Toast公共方法，使用：AppContext.getInstance().showToast("")
     */
    public void showToast(final String toast) {
        Message msg = new Message();
        msg.what = 1;
        msg.obj = toast;
        mToastHandler.sendMessage(msg);
    }

    private Handler mToastHandler = new Handler(msg -> {
        String toast = (String) msg.obj;
        Toast.makeText(AppContext.this, toast, Toast.LENGTH_SHORT).show();
        return false;
    });

    public void startWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
