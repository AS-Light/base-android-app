package jx.android.staff.binding.handler;

import android.content.Context;

import jx.android.staff.binding.vm.MainActivityViewModel;
import jx.android.staff.binding.handler.delegate.base.Delegate;
import jx.android.staff.binding.handler.base.BaseHandler;

public class MainActivityHandler extends BaseHandler<MainActivityViewModel, Delegate> {

    public MainActivityHandler(Context context, MainActivityViewModel viewModel) {
        super(context, viewModel);
    }
}
