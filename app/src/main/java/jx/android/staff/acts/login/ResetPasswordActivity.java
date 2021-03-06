package jx.android.staff.acts.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.aslight.titlebar.widget.CommonTitleBar;

import jx.android.staff.R;
import jx.android.staff.acts.WelcomeActivity;
import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.api.modelobserver.ResetPasswordObserver;
import jx.android.staff.api.modelobserver.UserInfoObserver;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.app.AppContext;
import jx.android.staff.binding.handler.ResetPasswordHandler;
import jx.android.staff.binding.handler.delegate.ResetPasswordDelegate;
import jx.android.staff.binding.vm.ResetPasswordViewModel;
import jx.android.staff.databinding.ActivityResetPasswordBinding;
import jx.android.staff.utils.StringUtils;

public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding> implements ResetPasswordDelegate {

    ResetPasswordObserver mResetPasswordObserver = new ResetPasswordObserver();

    ResetPasswordViewModel mViewModel;
    ResetPasswordHandler mBindingHandler;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initBinding() {
        mViewModel = new ResetPasswordViewModel();
        mBindingHandler = new ResetPasswordHandler(this, mViewModel, this);

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

        mResetPasswordObserver.isSmsSend.observe(this, success -> {
            hideWaitDialog();
            if (success != null && success) {
                onSmsSendSuccess();
            }
        });
        mResetPasswordObserver.isResetSuccess.observe(this, success -> {
            hideWaitDialog();
            if (success != null && success) {
                AppContext.getInstance().showToast("????????????????????????????????????");
                AppContext.getInstance().removeUserInfo();
                AppContext.getInstance().removeLoginInfo();
                UserInfoObserver.getInstance().getUserInfo().postValue(null);
                Intent intent = new Intent(ResetPasswordActivity.this, WelcomeActivity.class);
                startActivity(intent);
            } else {
                // AppContext.getInstance().showToast("???????????????????????????????????????????????????");
            }
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initData() {

    }

    @Override
    public String getActivityName() {
        return "????????????";
    }

    @SuppressLint("SetTextI18n")
    private void onSmsSendSuccess() {
        mViewBinding.buttonSms.setText("60????????????");
        mViewBinding.buttonSms.setEnabled(false);
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mViewBinding.buttonSms.setText(millisUntilFinished / 1000 + "????????????");
            }

            @Override
            public void onFinish() {
                mViewBinding.buttonSms.setText("???????????????");
                mViewBinding.buttonSms.setEnabled(true);
            }
        };
        timer.start();
    }

    @Override
    public void onConfirm() {
        // todo: ????????????????????????
        LoginParam loginParam = mViewModel.loginParam.get();
        assert loginParam != null;
        if (!StringUtils.isPhone(loginParam.username)) {
            Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
        } else if (StringUtils.isEmpty(loginParam.verifyCode)) {
            Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
        } else if (!StringUtils.isPassword(loginParam.password)) {
            Toast.makeText(this, "?????????????????????8-16?????????????????????????????????", Toast.LENGTH_SHORT).show();
        } else {
            showWaitDialog();
            mResetPasswordObserver.resetPassword(loginParam);
        }


    }

    @Override
    public void onClickSms() {
        // todo: ???????????????????????????
        LoginParam loginParam = mViewModel.loginParam.get();
        assert loginParam != null;
        if (!StringUtils.isPhone(loginParam.username)) {
            Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
        } else {
            showWaitDialog();
            mResetPasswordObserver.sendSms(loginParam.username);
        }
    }
}
