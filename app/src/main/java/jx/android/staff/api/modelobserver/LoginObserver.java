package jx.android.staff.api.modelobserver;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import jx.android.staff.api.Api;
import jx.android.staff.api.callback.OnHttpCallbackListener;
import jx.android.staff.api.callback.OnHttpCallbackSub;
import jx.android.staff.api.model.LoginInfo;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.app.AppCache;
import jx.android.staff.app.AppContext;

public class LoginObserver extends ViewModel {

    public MutableLiveData<LoginInfo> loginInfo = new MutableLiveData<>();

    public void loginWithPassword(LoginParam param) {
        Api.getInstance().loginWithPassword(param, new OnHttpCallbackSub(new OnHttpCallbackListener() {
            @Override
            public void onSuccess(String result) {
                loginInfo.postValue(LoginInfo.fromJson(result));
                AppCache.getInstance().cacheLoginParam(param);
            }

            @Override
            public void onFault(String errorMsg) {
                loginInfo.postValue(null);
                AppContext.getInstance().showToast(errorMsg);
            }
        }));
    }
}
