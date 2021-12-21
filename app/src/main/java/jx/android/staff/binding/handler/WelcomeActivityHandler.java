package jx.android.staff.binding.handler;

import android.content.Context;

import jx.android.staff.acts.login.LoginWithPassActivity;
import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.base.Delegate;
import jx.android.staff.binding.vm.WelcomeActivityViewModel;

public class WelcomeActivityHandler extends BaseHandler<WelcomeActivityViewModel, Delegate> {

    public WelcomeActivityHandler(Context context, WelcomeActivityViewModel viewModel) {
        super(context, viewModel);
    }
}
