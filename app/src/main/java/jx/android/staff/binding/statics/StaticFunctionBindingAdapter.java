package jx.android.staff.binding.statics;


import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

public class StaticFunctionBindingAdapter {

    @BindingAdapter("onBannerClick")
    public static void setOnBannerClickListener(Banner banner, OnBannerClickListener listener) {
        banner.setOnBannerClickListener(listener);
    }

    @BindingAdapter("onBannerChanged")
    public static void setOnBannerChangeListener(Banner banner, ViewPager.OnPageChangeListener listener) {
        banner.setOnPageChangeListener(listener);
    }
}
