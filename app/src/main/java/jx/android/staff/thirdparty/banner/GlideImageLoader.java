package jx.android.staff.thirdparty.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    private int mDefDrawableRes;

    public GlideImageLoader(int defDrawableRes) {
        mDefDrawableRes = defDrawableRes;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .error(mDefDrawableRes)
                .fallback(mDefDrawableRes)
                .into(imageView);
    }
}
