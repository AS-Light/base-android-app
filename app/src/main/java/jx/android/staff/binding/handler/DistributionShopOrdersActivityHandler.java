package jx.android.staff.binding.handler;

import android.content.Context;
import android.os.Bundle;

import jx.android.staff.acts.distribution.DistributionPersonOrderDetailActivity;
import jx.android.staff.acts.distribution.DistributionShopOrdersActivity;
import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.base.Delegate;
import jx.android.staff.binding.vm.DistributionPersonOrdersActivityViewModel;
import jx.android.staff.binding.vm.DistributionShopOrdersActivityViewModel;

public class DistributionShopOrdersActivityHandler extends BaseHandler<DistributionShopOrdersActivityViewModel, Delegate> {

    public DistributionShopOrdersActivityHandler(Context context, DistributionShopOrdersActivityViewModel viewModel) {
        super(context, viewModel);
    }

    public void onClickItem(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("person_order_id", id);
        startActivity(DistributionPersonOrderDetailActivity.class, bundle);
    }
}
