package jx.android.staff.binding.handler;

import android.content.Context;
import android.os.Bundle;

import java.text.ParseException;
import java.util.Date;

import jx.android.staff.acts.distribution.DistributionShopOrdersActivity;
import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.DistributionMainShopFragmentDelegate;
import jx.android.staff.binding.handler.delegate.base.Delegate;
import jx.android.staff.binding.vm.DistributionMainShopFragmentViewModel;
import jx.android.staff.utils.DateUtils;

public class DistributionMainShopFragmentHandler extends BaseHandler<DistributionMainShopFragmentViewModel, DistributionMainShopFragmentDelegate> {

    private Long DAY = 24 * 60 * 60 * 1000L;

    public DistributionMainShopFragmentHandler(Context context, DistributionMainShopFragmentViewModel viewModel, DistributionMainShopFragmentDelegate delegate) {
        super(context, viewModel, delegate);
    }

    public void onClickLastDate() {
        try {
            long dateL = DateUtils.strTime2Long(mViewModel.nowDateStr.get(), "yyyy-MM-dd");
            String lastDateStr = DateUtils.long2StrTime(dateL - DAY, "yyyy-MM-dd", "8");
            mViewModel.nowDateStr.set(lastDateStr);
            mDelegate.onDateChange();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClickNextDate() {
        try {
            long dateL = DateUtils.strTime2Long(mViewModel.nowDateStr.get(), "yyyy-MM-dd");
            String lastDateStr = DateUtils.long2StrTime(dateL + DAY, "yyyy-MM-dd", "8");
            mViewModel.nowDateStr.set(lastDateStr);
            mDelegate.onDateChange();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClickItem(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("shop_order_id", id);
        startActivity(DistributionShopOrdersActivity.class, bundle);
    }
}
