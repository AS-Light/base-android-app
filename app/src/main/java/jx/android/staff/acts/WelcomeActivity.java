package jx.android.staff.acts;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import jx.android.staff.R;
import jx.android.staff.acts.MainActivity;
import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.api.modelobserver.LoginObserver;
import jx.android.staff.api.modelobserver.UserInfoObserver;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.app.AppCache;
import jx.android.staff.app.AppContext;
import jx.android.staff.app.AppLog;
import jx.android.staff.app.AppManager;
import jx.android.staff.app.Keys;
import jx.android.staff.binding.handler.WelcomeActivityHandler;
import jx.android.staff.binding.vm.WelcomeActivityViewModel;
import jx.android.staff.databinding.ActivityWelcomeBinding;

/**
 * WelcomeActivity
 *
 */
public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding> {
    WelcomeActivityViewModel mViewModel;
    WelcomeActivityHandler mHandler;

    LoginObserver mLoginObserver = new LoginObserver();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initBinding() {
        mViewModel = new WelcomeActivityViewModel();
        mHandler = new WelcomeActivityHandler(this, mViewModel);

        mViewBinding.setViewModel(mViewModel);
        mViewBinding.setHandler(mHandler);
    }

    @Override
    public void initViews() {
        mViewBinding.layoutRoot.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initObserver() {
        mLoginObserver.loginInfo.observe(this, loginInfo -> {
            Log.e("login info", new Gson().toJson(loginInfo));
            if (loginInfo != null) {
                onLoginSuccess();
            } else {
                hideWaitDialog();
            }
        });
        UserInfoObserver.getInstance().getUserInfo().observe(this, userInfo -> {
            if (userInfo != null) {
                AppCache.getInstance().cacheUserInfo(userInfo);
                startActivity(new Intent(this, MainActivity.class));
                hideWaitDialog();
                finish();
            } else {
                AppCache.getInstance().removeUserInfo();
                mViewBinding.layoutRoot.setVisibility(View.VISIBLE);
                hideWaitDialog();
            }
        });
    }

    @Override
    public void initData() {
        // 两次Back退出app
        couldExitApp = true;

        LoginParam loginParam = AppCache.getInstance().getLoginParam();
        if (loginParam != null) {
            mLoginObserver.loginWithPassword(loginParam);
        } else {
            mViewBinding.layoutRoot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getActivityName() {
        return "欢迎页";
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        UserInfoObserver.getInstance().getUserInfo().removeObservers(this);
        mLoginObserver.loginInfo.removeObservers(this);
        super.onDestroy();
    }

    private void onLoginSuccess() {
        // 已通过审核，尝试获取用户信息，如获取用户信息成功进入MainActivity
        showWaitDialog();
        changeWaitMessage("正在获取慧集鲜用户信息... ...");
        UserInfoObserver.getInstance().requestUserInfo();
    }
}
