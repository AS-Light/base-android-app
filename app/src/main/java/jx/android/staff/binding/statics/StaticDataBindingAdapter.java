package jx.android.staff.binding.statics;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import jx.android.staff.R;

import jx.android.staff.thirdparty.banner.GlideImageLoader;
import jx.android.staff.app.AppContext;
import jx.android.staff.utils.StringUtils;
import com.youth.banner.Banner;

import java.io.File;
import java.util.List;

public class StaticDataBindingAdapter {

    @BindingAdapter(value = {"imageUrl", "imagePath", "placeHolder", "error", "hideWhenNull"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, String path, Drawable holderDrawable, Drawable errorDrawable, boolean hideWhenNull) {
        RequestManager glideManager = Glide.with(imageView.getContext());
        RequestBuilder<Drawable> glideBuilder = null;
        if (!StringUtils.isEmpty(url)) {
            glideBuilder = glideManager.load(url);
        } else if (!StringUtils.isEmpty(path)) {
            glideBuilder = glideManager.load(new File(path));
        }
        // todo: 判断是否包含url或者path，如无则判断hideWhenNull值，隐藏或显示errorDrawable
        if (glideBuilder != null) {
            glideBuilder.placeholder(holderDrawable)
                    .error(errorDrawable)
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
        } else {
            if (hideWhenNull) {
                imageView.setVisibility(View.GONE);
            } else {
                imageView.setImageDrawable(errorDrawable);
            }
        }
    }

    /**
     * 设置layout中Banner的images属性，并监听值变化
     */
    @BindingAdapter(value = {"images", "autoPlay"}, requireAll = false)
    public static void setImages(Banner view, List<String> images, Boolean autoPlay) {
        if (images != null) {
            view.setImageLoader(new GlideImageLoader(R.drawable.svg_photo_def));
            view.update(images);
            if (autoPlay == null || autoPlay) {
                view.startAutoPlay();
            }
        }
    }

    /**
     * 验证码再次发送倒计时的显示
     */
    @BindingAdapter({"sendSmsCountDown"})
    public static void setSendSmsCountDown(TextView button, Integer sendSmsCountDown) {
        if (sendSmsCountDown < 0) {
            button.setText(AppContext.getInstance().getString(R.string.send_sms));
            button.setEnabled(true);
        } else {
            button.setText(AppContext.getInstance().getString(R.string.retry_after_seconds_with_value, sendSmsCountDown));
            if (button.isEnabled()) {
                button.setEnabled(false);
            }
        }
    }

    /**
     * 以年月日格式化日期，日期以年月日数组传递
     */
    @BindingAdapter({"dates"})
    public static void setDate(TextView textView, String[] dates) {
        textView.setText(String.format("%s年%s月%s日", dates[0], dates[1], dates[2]));
    }

    /**
     * 是否使用秒卖房数字字体
     */
    @BindingAdapter({"useOTF"})
    public static void useOTF(TextView view, boolean useOTF) {
        if (useOTF) {
            Typeface typeface = Typeface.createFromAsset(AppContext.getInstance().getAssets(), "fonts/mmf_num.otf");
            view.setTypeface(typeface);
        }
    }

    /**
     * 添加TextView字体特效
     * */
    @BindingAdapter({"paintFlag"})
    public static void setTextPaintFlag(TextView textView, int paintFlag) {
        textView.getPaint().setFlags(paintFlag);
    }
}
