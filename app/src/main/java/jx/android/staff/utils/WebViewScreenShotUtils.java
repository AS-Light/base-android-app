package jx.android.staff.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Build;
import android.webkit.WebView;

/**
 * 长截图工具类
 *
 * @author Administrator
 * @date 2018/5/21
 */

public class WebViewScreenShotUtils {

    /**
     * 截图WebView，没有使用X5内核
     *
     * @param webView
     * @return
     */
    public static Bitmap shotWebView(WebView webView) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Android5.0以上
                float scale = webView.getScale();
                int width = webView.getWidth();
                int height = (int) (webView.getContentHeight() * scale + 0.5);
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                webView.draw(canvas);

                // 保存图片
                return bitmap;
            } else {
                // Android5.0以下
                Picture picture = webView.capturePicture();
                int width = picture.getWidth();
                int height = picture.getHeight();
                if (width > 0 && height > 0) {
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    picture.draw(canvas);

                    return bitmap;
                }
                return null;
            }
        } catch (OutOfMemoryError oom) {
            return null;
        }
    }
}