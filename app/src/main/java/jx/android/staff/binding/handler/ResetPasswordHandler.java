package jx.android.staff.binding.handler;

import android.content.Context;
import android.view.View;

import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.ResetPasswordDelegate;
import jx.android.staff.binding.vm.ResetPasswordViewModel;

public class ResetPasswordHandler extends BaseHandler<ResetPasswordViewModel, ResetPasswordDelegate> {

    public ResetPasswordHandler(Context context, ResetPasswordViewModel viewModel, ResetPasswordDelegate delegate) {
        super(context, viewModel, delegate);
    }

    public void onConfirm(View view) {
        mDelegate.onConfirm();
    }

    public void onClickSms(View view) {
        mDelegate.onClickSms();
    }

    public void showPassword() {
        mViewModel.showAsPassword.set(!mViewModel.showAsPassword.get());
    }
}
