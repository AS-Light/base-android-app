package jx.android.staff.api.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jx.android.staff.api.modelobserver.UserInfoObserver;
import jx.android.staff.app.AppContext;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Objects;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Http请求的统一订阅和错误处理
 */
public class OnHttpCallbackSub extends DisposableObserver<ResponseBody> {
    private OnHttpCallbackListener mOnHttpCallbackListener;

    /**
     * @param mOnHttpCallbackListener 成功回调监听
     */
    public OnHttpCallbackSub(OnHttpCallbackListener mOnHttpCallbackListener) {
        this.mOnHttpCallbackListener = mOnHttpCallbackListener;
    }

    @Override
    public void onStart() {
        // TODO: 接口调用开始
    }

    @Override
    public void onComplete() {
        // TODO: 接口调用结束（不论对错）
    }


    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     */
    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof SocketTimeoutException) {
                mOnHttpCallbackListener.onFault("网络连接超时");
            } else if (e instanceof ConnectException) {
                mOnHttpCallbackListener.onFault("网络连接失败");
            } else if (e instanceof SSLHandshakeException) {
                mOnHttpCallbackListener.onFault("安全证书异常");
            } else if (e instanceof UnknownHostException) {
                mOnHttpCallbackListener.onFault("域名解析失败");
            } else if (e instanceof HttpException) {
                int code = ((HttpException) e).code();
                if (code == 504) {
                    mOnHttpCallbackListener.onFault("网络异常，请检查您的网络状态");
                } else if (code == 404) {
                    mOnHttpCallbackListener.onFault("请求的地址不存在");
                } else if (code == 401 || code == 10086) {
                    // 如果当前缓存的账号信息不空，删除账号缓存，打开登陆界面
                    // 进行判断的目的是防止多个接口在反复返回401错误
                    if (AppContext.getInstance().getUserInfo() != null) {
                        mOnHttpCallbackListener.onFault(code == 401 ? "您的账号已经在另一个终端登录，请重新登录，如有疑问请联系客服" : "您的VIP状态已变更，请重新登录，如有疑问请联系客服");
                        UserInfoObserver.getInstance().getUserInfo().postValue(null);
                        AppContext.getInstance().removeUserInfo();
                        AppContext.getInstance().removeLoginInfo();
                        AppContext.getInstance().startWelcomeActivity();
                    } else {
                        mOnHttpCallbackListener.onFault(code == 401 ? "您的账号已经在另一个终端登录，请重新登录，如有疑问请联系客服" : "您的VIP状态已变更，请重新登录，如有疑问请联系客服");
                    }
                } else {
                    try {
                        HashMap<String, String> resultMap = new Gson().fromJson(Objects.requireNonNull(Objects.requireNonNull(((HttpException) e).response()).errorBody()).string(),
                                new TypeToken<HashMap<String, String>>() {
                                }.getType());
                        mOnHttpCallbackListener.onFault(resultMap.get("message"));
                    } catch (Exception exception) {
                        mOnHttpCallbackListener.onFault("请求失败, code = " + code);
                    }
                }
            } else {
                mOnHttpCallbackListener.onFault("error:" + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }


    /**
     * 当result等于1回调给调用者，否则自动显示错误信息，若错误信息为401跳转登录页面。
     * ResponseBody  body = response.body();//获取响应体
     * InputStream inputStream = body.byteStream();//获取输入流
     * byte[] bytes = body.bytes();//获取字节数组
     * String str = body.string();//获取字符串数据
     */
    @Override
    public void onNext(ResponseBody body) {
        try {
            final String result = body.string();
            SimpleCallbackInfo simpleCallbackInfo = new Gson().fromJson(result, SimpleCallbackInfo.class);
            if (simpleCallbackInfo.getCode() == 0) {
                // 接口正常调用
                mOnHttpCallbackListener.onSuccess(result);
            } else {
                // 接口异常
                mOnHttpCallbackListener.onFault(simpleCallbackInfo.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
