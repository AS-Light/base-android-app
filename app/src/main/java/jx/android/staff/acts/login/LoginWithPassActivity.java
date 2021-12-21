package jx.android.staff.acts.login;

import jx.android.staff.R;

import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.api.modelobserver.LoginObserver;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.app.AppContext;
import jx.android.staff.binding.handler.LoginHandler;
import jx.android.staff.binding.handler.delegate.LoginDelegate;
import jx.android.staff.binding.vm.LoginViewModel;
import jx.android.staff.databinding.ActivityLoginWithPassBinding;
import com.aslight.titlebar.widget.CommonTitleBar;

public class LoginWithPassActivity extends BaseActivity<ActivityLoginWithPassBinding> implements LoginDelegate {

    LoginObserver mLoginObserver = new LoginObserver();

    LoginViewModel mViewModel;
    LoginHandler mBindingHandler;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login_with_pass;
    }

    @Override
    public void initBinding() {
        mViewModel = new LoginViewModel();
        mViewModel.setLoginParam(new LoginParam());
        mBindingHandler = new LoginHandler(this, mViewModel, this);

        mViewBinding.setViewModel(mViewModel);
        mViewBinding.setHandler(mBindingHandler);
    }

    @Override
    public void initViews() {
        mViewBinding.titleBar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initObserver() {
        mLoginObserver.loginInfo.observe(this, loginInfo -> {
            hideWaitDialog();
            if (loginInfo != null) {
                AppContext.getInstance().cacheLoginInfo(loginInfo);
                finish();
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    public String getActivityName() {
        return "密码登录";
    }

    @Override
    public void login(LoginParam loginParam) {
        showWaitDialog();
        mLoginObserver.loginWithPassword(loginParam);
    }
}