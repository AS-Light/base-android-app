package jx.android.staff.utils;

import android.annotation.SuppressLint;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtils {

    @SuppressLint("CheckResult")
    public static RequestOptions getUnCacheOptions() {
        RequestOptions requestOptions = new RequestOptions();
        //禁用磁盘缓存
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);///不使用磁盘缓存
        requestOptions.skipMemoryCache(true);
        return requestOptions;
    }
}
