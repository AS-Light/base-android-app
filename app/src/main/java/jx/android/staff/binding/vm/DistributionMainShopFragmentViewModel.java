package jx.android.staff.binding.vm;

import androidx.databinding.ObservableField;

import java.util.Date;

import jx.android.staff.binding.vm.base.BaseViewModel;
import jx.android.staff.utils.DateUtils;

public class DistributionMainShopFragmentViewModel extends BaseViewModel {
    public ObservableField<String> nowDateStr = new ObservableField<>();

    public DistributionMainShopFragmentViewModel() {
        Date date = new Date();
        String dateStr = DateUtils.date2Str(date, "yyyy-MM-dd");
        nowDateStr.set(dateStr);
    }
}
