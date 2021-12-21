package jx.android.staff.binding.vm;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import jx.android.staff.api.params.LoginParam;
import jx.android.staff.binding.vm.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel {
    public ObservableField<LoginParam> loginParam = new ObservableField<>();
    public ObservableBoolean showAsPassword = new ObservableBoolean(true);

    public void setLoginParam(LoginParam loginParam) {
        this.loginParam.set(loginParam);
    }
}
