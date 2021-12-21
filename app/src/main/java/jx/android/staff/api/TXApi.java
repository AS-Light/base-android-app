package jx.android.staff.api;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class TXApi {

    private static TXApi instance;

    public static TXApi getInstance() {
        if (instance == null) {
            instance = new TXApi();
        }
        return instance;
    }

    private TXHttpClientManager mHttpManager;
    private ApiObserverService mApiService;

    TXApi() {
        mHttpManager = TXHttpClientManager.getInstance();
        mApiService = mHttpManager.create(ApiObserverService.class);
    }

    /**
     * 获取腾讯地图街景poi
     *
     * @param location   坐标点位置
     * @param key        ak
     * @param subscriber
     */
    public void getTencentPoi(String location, String key, DisposableObserver<ResponseBody> subscriber) {
        TXHttpClientManager mTXHttpClientManager = TXHttpClientManager.getInstance();
        mTXHttpClientManager.toSubscribe(mApiService.getTencentPoi(location, key), subscriber);
    }
}
