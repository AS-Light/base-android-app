package jx.android.staff.binding.handler;

import android.content.Context;

import java.text.ParseException;

import jx.android.staff.binding.handler.base.BaseHandler;
import jx.android.staff.binding.handler.delegate.StorehouseFragmentDelegate;
import jx.android.staff.binding.vm.StorehouseFragmentViewModel;
import jx.android.staff.utils.DateUtils;

public class StorehouseFragmentHandler extends BaseHandler<StorehouseFragmentViewModel, StorehouseFragmentDelegate> {

    private Long DAY = 24 * 60 * 60 * 1000L;

    public StorehouseFragmentHandler(Context context, StorehouseFragmentViewModel viewModel, StorehouseFragmentDelegate delegate) {
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
