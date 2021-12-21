package jx.android.staff.acts.distribution;

import android.content.Context;
import android.view.View;

import androidx.viewbinding.ViewBinding;

import jx.android.staff.BR;
import jx.android.staff.R;
import jx.android.staff.acts.base.easyrecycler.BaseEasyAdapter;
import jx.android.staff.acts.base.easyrecycler.BaseEasyHolder;
import jx.android.staff.api.model.items.PersonOrderItem;
import jx.android.staff.databinding.ItemDistributionGroupByPersonBinding;

public class DistributionPersonOrderDetailAdapter extends BaseEasyAdapter<PersonOrderItem, ItemDistributionGroupByPersonBinding> {

    public DistributionPersonOrderDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_main_distribution_shop;
    }

    @Override
    public BaseEasyHolder<PersonOrderItem, ItemDistributionGroupByPersonBinding> buildHolder(ItemDistributionGroupByPersonBinding dataBinding) {
        return new DistributionPersonOrderHolder(dataBinding.getRoot());
    }

    static class DistributionPersonOrderHolder extends BaseEasyHolder<PersonOrderItem, ItemDistributionGroupByPersonBinding> {

        DistributionPersonOrderHolder(View itemView) {
            super(itemView);
        }

        @Override
        public int getVariableId() {
            return BR.data;
        }

        @Override
        public void parseData(PersonOrderItem data) {

        }

    }
}