package jx.android.staff.binding.handler;

import android.content.Context;

import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.base.Delegate;
import jx.android.staff.binding.vm.DistributionGoodsOrdersActivityViewModel;

public class DistributionGoodsOrdersActivityHandler extends BaseHandler<DistributionGoodsOrdersActivityViewModel, Delegate> {

    public DistributionGoodsOrdersActivityHandler(Context context, DistributionGoodsOrdersActivityViewModel viewModel) {
        super(context, viewModel);
    }
}
