package jx.android.staff.acts.distribution;

import android.content.Context;
import android.view.View;

import jx.android.staff.BR;
import jx.android.staff.R;
import jx.android.staff.acts.base.easyrecycler.BaseEasyAdapter;
import jx.android.staff.acts.base.easyrecycler.BaseEasyHolder;
import jx.android.staff.databinding.ItemMainDistrbutionGoodsBinding;

public class DistributionGoodsOrdersAdapter extends BaseEasyAdapter<Integer, ItemMainDistrbutionGoodsBinding> {

    public DistributionGoodsOrdersAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_main_distribution_shop;
    }

    @Override
    public BaseEasyHolder<Integer, ItemMainDistrbutionGoodsBinding> buildHolder(ItemMainDistrbutionGoodsBinding dataBinding) {
        return new DistributionHolder(dataBinding.getRoot());
    }

    static class DistributionHolder extends BaseEasyHolder<Integer, ItemMainDistrbutionGoodsBinding> {

        DistributionHolder(View itemView) {
            super(itemView);
        }

        @Override
        public int getVariableId() {
            return BR.data;
        }

        @Override
        public void parseData(Integer data) {

        }

    }
}