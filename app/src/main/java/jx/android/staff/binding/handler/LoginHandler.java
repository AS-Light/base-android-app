package jx.android.staff.binding.handler;

import android.content.Context;

import jx.android.staff.binding.vm.LoginViewModel;
import jx.android.staff.acts.login.LoginWithPassActivity;
import jx.android.staff.acts.login.ResetPasswordActivity;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.app.AppContext;
import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.LoginDelegate;
import jx.android.staff.utils.StringUtils;

public class LoginHandler extends BaseHandler<LoginViewModel, LoginDelegate> {

    public LoginHandler(Context context, LoginViewModel viewModel, LoginDelegate delegate) {
        super(context, viewModel, delegate);
    }

    public void login() {
        LoginParam loginParam = mViewModel.loginParam.get();
        String errorMsg = checkLoginParamWithPassword();

        if (errorMsg  == null) {
            mDelegate.login(loginParam);
        } else {
            AppContext.getInstance().showToast(errorMsg);
        }
    }

    public void showPassword() {
        mViewModel.showAsPassword.set(!mViewModel.showAsPassword.get());
    }

    public void findPassword() {
        startActivity(ResetPasswordActivity.class);
    }

    public String checkLoginParamWithPassword() {
        LoginParam loginParam = mViewModel.loginParam.get();
        assert loginParam != null;
        if (!StringUtils.isPhone(loginParam.username)) {
            return "请输入正确的手机号码";
        } else if (StringUtils.isEmpty(loginParam.password)) {
            return "请输入密码";
        } else {
            return null;
        }
    }
}
