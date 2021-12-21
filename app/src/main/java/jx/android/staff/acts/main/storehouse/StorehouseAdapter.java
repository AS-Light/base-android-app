package jx.android.staff.acts.main.storehouse;

import android.content.Context;
import android.view.View;

import jx.android.staff.BR;
import jx.android.staff.R;
import jx.android.staff.acts.base.easyrecycler.BaseEasyAdapter;
import jx.android.staff.acts.base.easyrecycler.BaseEasyHolder;
import jx.android.staff.api.model.items.StorehouseItem;
import jx.android.staff.databinding.ItemMainStorehouseBinding;

public class StorehouseAdapter extends BaseEasyAdapter<StorehouseItem, ItemMainStorehouseBinding> {

    public StorehouseAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_main_storehouse;
    }

    @Override
    public BaseEasyHolder<StorehouseItem, ItemMainStorehouseBinding> buildHolder(ItemMainStorehouseBinding dataBinding) {
        return new StorehouseHolder(dataBinding.getRoot());
    }

    static class StorehouseHolder extends BaseEasyHolder<StorehouseItem, ItemMainStorehouseBinding> {

        StorehouseHolder(View itemView) {
            super(itemView);
        }

        @Override
        public int getVariableId() {
            return BR.data;
        }

        @Override
        public void parseData(StorehouseItem data) {

        }

    }
}