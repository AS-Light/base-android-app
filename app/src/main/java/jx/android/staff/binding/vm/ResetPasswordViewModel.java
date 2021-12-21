package jx.android.staff.binding.vm;


import androidx.databinding.ObservableField;

import jx.android.staff.api.params.LoginParam;
import jx.android.staff.binding.vm.base.BaseViewModel;

public class ResetPasswordViewModel extends BaseViewModel {
    public ObservableField<LoginParam> loginParam = new ObservableField<>(new LoginParam());
    public ObservableField<Boolean> showAsPassword = new ObservableField<>(true);
}
