package jx.android.staff.binding.handler;

import android.content.Context;

import java.text.ParseException;

import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.DistributionMainGoodsFragmentDelegate;
import jx.android.staff.binding.handler.delegate.base.Delegate;
import jx.android.staff.binding.vm.DistributionMainGoodsFragmentViewModel;
import jx.android.staff.utils.DateUtils;

public class DistributionMainGoodsFragmentHandler extends BaseHandler<DistributionMainGoodsFragmentViewModel, DistributionMainGoodsFragmentDelegate> {

    private Long DAY = 24 * 60 * 60 * 1000L;

    public DistributionMainGoodsFragmentHandler(Context context, DistributionMainGoodsFragmentViewModel viewModel, DistributionMainGoodsFragmentDelegate delegate) {
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
}
