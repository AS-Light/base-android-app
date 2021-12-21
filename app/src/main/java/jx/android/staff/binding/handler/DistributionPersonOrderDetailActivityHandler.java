package jx.android.staff.binding.handler;

import android.content.Context;

import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.base.Delegate;
import jx.android.staff.binding.vm.DistributionPersonOrderDetailActivityViewModel;

public class DistributionPersonOrderDetailActivityHandler extends BaseHandler<DistributionPersonOrderDetailActivityViewModel, Delegate> {

    public DistributionPersonOrderDetailActivityHandler(Context context, DistributionPersonOrderDetailActivityViewModel viewModel) {
        super(context, viewModel);
    }
}
