package jx.android.staff.api.modelobserver;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import jx.android.staff.api.callback.OnHttpCallbackListener;
import jx.android.staff.api.callback.OnHttpCallbackSub;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.api.Api;
import jx.android.staff.app.AppContext;

public class ResetPasswordObserver extends ViewModel {

    public MutableLiveData<Boolean> isSmsSend = new MutableLiveData<>();
    public MutableLiveData<Boolean> isResetSuccess = new MutableLiveData<>();

    public void sendSms(String phone) {
        Api.getInstance().sendSms(phone, new OnHttpCallbackSub(new OnHttpCallbackListener() {
            @Override
            public void onSuccess(String result) {
                isSmsSend.postValue(true);
            }

            @Override
            public void onFault(String errorMsg) {
                AppContext.getInstance().showToast(errorMsg);
                isSmsSend.postValue(false);
            }
        }));
    }

    public void resetPassword(LoginParam param) {
        Api.getInstance().setPassword(param, new OnHttpCallbackSub(new OnHttpCallbackListener() {
            @Override
            public void onSuccess(String result) {
                isResetSuccess.postValue(true);
            }

            @Override
            public void onFault(String errorMsg) {
                AppContext.getInstance().showToast(errorMsg);
                isResetSuccess.postValue(false);
            }
        }));
    }
}
