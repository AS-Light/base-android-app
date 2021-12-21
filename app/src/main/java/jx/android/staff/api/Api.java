package jx.android.staff.api;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.api.params.ShipTimeParam;
import jx.android.staff.app.AppLog;
import okhttp3.ResponseBody;

public class Api {

    private static Api instance;

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    private HttpClientManager mHttpManager;
    private ApiObserverService mApiService;

    private Api() {
        mHttpManager = HttpClientManager.getInstance();
        mApiService = mHttpManager.create(ApiObserverService.class);
    }

    public void sendSms(String phone, DisposableObserver<ResponseBody> subscriber) {
        Map<String, Object> param = new HashMap<>();
        param.put("tell", phone);
        mHttpManager.toSubscribe(mApiService.sendSms(param), subscriber);
    }

    public void loginWithPassword(LoginParam param, DisposableObserver<ResponseBody> subscriber) {
        mHttpManager.toSubscribe(mApiService.loginWithPassword(param), subscriber);
    }

    public void loginWithSms(LoginParam param, DisposableObserver<ResponseBody> subscriber) {
        mHttpManager.toSubscribe(mApiService.loginWithSms(param), subscriber);
    }

    public void setPassword(LoginParam param, DisposableObserver<ResponseBody> subscriber) {
        mHttpManager.toSubscribe(mApiService.setPassword(param), subscriber);
    }

    public void getUserInfo(DisposableObserver<ResponseBody> subscriber) {
        mHttpManager.toSubscribe(mApiService.getUserInfo(), subscriber);
    }

    public void getPurchaseList(String shipTimeStr, DisposableObserver<ResponseBody> subscriber) {
        ShipTimeParam param = new ShipTimeParam();
        param.setShipTimeStr(shipTimeStr);
        mHttpManager.toSubscribe(mApiService.getPurchaseList(param), subscriber);
    }

    public void getDistributionShopList(String shipTimeStr, DisposableObserver<ResponseBody> subscriber) {
        ShipTimeParam param = new ShipTimeParam();
        param.setShipTimeStr(shipTimeStr);
        mHttpManager.toSubscribe(mApiService.getDistributionShopList(param), subscriber);
    }

    public void getDistributionPersonList(Long id, DisposableObserver<ResponseBody> subscriber) {
        mHttpManager.toSubscribe(mApiService.getDistributionPersonList(id), subscriber);
    }

    public void getOrderDetail(Long id, DisposableObserver<ResponseBody> subscriber) {
        mHttpManager.toSubscribe(mApiService.getOrderDetail(id), subscriber);
    }
}
