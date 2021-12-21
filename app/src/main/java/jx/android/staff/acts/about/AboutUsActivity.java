package jx.android.staff.acts.about;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jx.android.staff.R;

import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.app.Config;
import jx.android.staff.databinding.ActivityAboutUsBinding;
import com.aslight.titlebar.widget.CommonTitleBar;

public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding> {


    @Override
    public int getLayoutRes() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initBinding() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initViews() {
        mViewBinding.titleBar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                finish();
            }
        });
//        webView.loadUrl("http://192.168.0.6:9401/snail-app/page/xcx/aboutme.html");
        mViewBinding.webview.loadUrl(Config.getAbsoluteH5Path("aboutme.html"));
        WebSettings settings = mViewBinding.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        mViewBinding.webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                //等待证书响应
//                handler.proceed();
//            }
//        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initData() {

    }

    @Override
    public String getActivityName() {
        return "关于我们";
    }
}
