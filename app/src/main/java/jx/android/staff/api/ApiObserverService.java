package jx.android.staff.api;

import java.util.Map;

import io.reactivex.Observable;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.api.params.ShipTimeParam;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiObserverService {

    /**
     * 发送短信验证码
     */
    @POST("sms/sendverifycode")
    Observable<ResponseBody> sendSms(@Body Map param);

    /**
     * 密码登录
     */
    @POST("admin/sys/staff/login")
    Observable<ResponseBody> loginWithPassword(@Body LoginParam param);

    /**
     * 验证码登录
     */
    @POST("user/login/verifycode")
    Observable<ResponseBody> loginWithSms(@Body LoginParam param);

    /**
     * 修改密码
     */
    @PUT("user/settingpassword")
    Observable<ResponseBody> setPassword(@Body LoginParam param);


    /**
     * 获取用户信息
     */
    @POST("admin/sys/user/info")
    Observable<ResponseBody> getUserInfo();

    /**
     * 获取腾讯地图poi
     */
    @POST("ws/streetview/v1/getpano")
    Observable<ResponseBody> getTencentPoi(@Query("location") String location, @Query("key") String key);

    /**
     * 获取采购列表
     */
    @POST("admin/sys/staff/purchaseList")
    Observable<ResponseBody> getPurchaseList(@Body ShipTimeParam param);

    /**
     * 获取配送店列表
     */
    @POST("admin/sys/staff/distributionStoreList")
    Observable<ResponseBody> getDistributionShopList(@Body ShipTimeParam shipTimeStr);

    /**
     * 获取配送店详情（店内订单列表）
     */
    @POST("admin/sys/staff/distributionStoreInfo/{id}")
    Observable<ResponseBody> getDistributionPersonList(@Path("id") Long id);

    /**
     * 获取订单详情
     */
    @POST("admin/sys/staff/orderInfo/{id}")
    Observable<ResponseBody> getOrderDetail(@Path("id") Long id);
}
