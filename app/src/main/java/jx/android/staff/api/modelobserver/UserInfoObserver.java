package jx.android.staff.api.modelobserver;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import jx.android.staff.api.Api;
import jx.android.staff.api.callback.OnHttpCallbackListener;
import jx.android.staff.api.callback.OnHttpCallbackSub;
import jx.android.staff.api.model.User;
import jx.android.staff.app.AppContext;

public class UserInfoObserver extends ViewModel {

    // 设置
    private static UserInfoObserver instance;

    public static UserInfoObserver getInstance() {
        if (instance == null) {
            instance = new UserInfoObserver();
        }
        return instance;
    }

    private MutableLiveData<User> userInfo = new MutableLiveData<>();
    private MutableLiveData<String> userHaveBeanCount = new MutableLiveData<>();

    public void requestUserInfo() {
        Api.getInstance().getUserInfo(new OnHttpCallbackSub(new OnHttpCallbackListener() {
            @Override
            public void onSuccess(String result) {
                User user = User.fromJson(result);
                UserInfoObserver.this.userInfo.postValue(user);
            }

            @Override
            public void onFault(String errorMsg) {
                userInfo.postValue(null);
                AppContext.getInstance().showToast(errorMsg);
            }
        }));
    }

    public MutableLiveData<User> getUserInfo() {
        return userInfo;
    }
}
