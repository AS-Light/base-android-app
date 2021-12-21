package jx.android.staff.acts.distribution;

import android.content.Context;
import android.view.View;

import jx.android.staff.BR;
import jx.android.staff.R;
import jx.android.staff.acts.base.easyrecycler.BaseEasyAdapter;
import jx.android.staff.acts.base.easyrecycler.BaseEasyHolder;
import jx.android.staff.api.model.items.PersonOrderItem;
import jx.android.staff.binding.handler.DistributionShopOrdersActivityHandler;
import jx.android.staff.databinding.ItemDistributionGroupByPersonBinding;

public class DistributionShopOrdersAdapter extends BaseEasyAdapter<PersonOrderItem, ItemDistributionGroupByPersonBinding> {

    DistributionShopOrdersActivityHandler mHandler;

    public DistributionShopOrdersAdapter(Context context) {
        super(context);
    }

    public void setHandler(DistributionShopOrdersActivityHandler handler) {
        mHandler = handler;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_distribution_group_by_person;
    }

    @Override
    public BaseEasyHolder<PersonOrderItem, ItemDistributionGroupByPersonBinding> buildHolder(ItemDistributionGroupByPersonBinding dataBinding) {
        return new DistributionShopHolder(dataBinding.getRoot());
    }

    class DistributionShopHolder extends BaseEasyHolder<PersonOrderItem, ItemDistributionGroupByPersonBinding> {
        DistributionShopHolder(View itemView) {
            super(itemView);
        }

        @Override
        public int getVariableId() {
            return BR.data;
        }

        @Override
        public void parseData(PersonOrderItem data) {
            getDataBinding().setHandler(mHandler);
        }

    }
}