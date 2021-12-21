package jx.android.staff.app;

import android.app.Application;

import com.google.gson.Gson;

import jx.android.staff.api.model.LoginInfo;
import jx.android.staff.api.model.User;
import jx.android.staff.api.params.LoginParam;
import jx.android.staff.utils.SharePreferenceUtils;

public class AppCache {

    private static AppCache mAppCacheInstance;

    /**
     * 获得AppCache单例
     */
    public static AppCache getInstance() {
        if (mAppCacheInstance == null) {
            mAppCacheInstance = new AppCache();
        }
        return mAppCacheInstance;
    }

    public void init(Application application) {
        mApp = application;
    }

    private static Application mApp;

    public void cacheLoginParam(LoginParam loginParam) {
        SharePreferenceUtils.putString(mApp, Keys.CACHE_KEY_LOGIN_PARAM, new Gson().toJson(loginParam));
    }

    // 缓存
    public LoginParam getLoginParam() {
        if (SharePreferenceUtils.contains(mApp, Keys.CACHE_KEY_LOGIN_PARAM)) {
            String loginParamJson = SharePreferenceUtils.getString(mApp, Keys.CACHE_KEY_LOGIN_PARAM);
            return new Gson().fromJson(loginParamJson, LoginParam.class);
        } else {
            return null;
        }
    }

    public void cacheUserInfo(User user) {
        SharePreferenceUtils.putString(mApp, Keys.CACHE_KEY_USER_INFO, new Gson().toJson(user));
    }

    public User getUserInfo() {
        if (SharePreferenceUtils.contains(mApp, Keys.CACHE_KEY_USER_INFO)) {
            String userInfoJson = SharePreferenceUtils.getString(mApp, Keys.CACHE_KEY_USER_INFO);
            return new Gson().fromJson(userInfoJson, User.class);
        } else {
            return null;
        }
    }

    public void removeUserInfo() {
        SharePreferenceUtils.remove(mApp, Keys.CACHE_KEY_USER_INFO);
    }
}
