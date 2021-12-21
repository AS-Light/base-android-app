package jx.android.staff.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jx.android.staff.R;

import jx.android.staff.utils.StringUtils;

import java.lang.ref.WeakReference;

public class WaitingDialog extends Dialog implements DialogInterface.OnCancelListener {

    private WeakReference<Context> mContext;
    private volatile static WaitingDialog instance;

    private TextView mTextMessage;

    private WaitingDialog(Context context, CharSequence message) {
        super(context, R.style.MMFDialog);

        mContext = new WeakReference<>(context);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_waiting, null);
        mTextMessage = view.findViewById(R.id.text_message);
        mTextMessage.setVisibility(StringUtils.isEmpty(message.toString()) ? View.GONE : View.VISIBLE);
        mTextMessage.setText(message);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);

        setOnCancelListener(this);
    }

    private void setMessage(CharSequence message) {
        mTextMessage.setVisibility(StringUtils.isEmpty(message.toString()) ? View.GONE : View.VISIBLE);
        mTextMessage.setText(message);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等
    }

    public static synchronized void showLoading(Context context) {
        showLoading(context, "loading...");
    }

    public static synchronized void showLoading(Context context, CharSequence message) {
        showLoading(context, message, false);
    }

    public static synchronized void showLoading(final Context context, final CharSequence message, final boolean cancelable) {
        new Thread(() -> new Handler(msg -> {
            if (instance != null && instance.isShowing()) {
                instance.dismiss();
            }

            if (context == null || !(context instanceof Activity)) {
                return false;
            }
            instance = new WaitingDialog(context, message);
            instance.setCancelable(cancelable);

            if (instance != null && !instance.isShowing() && !((Activity) context).isFinishing()) {
                instance.show();
            }
            return false;
        }).sendEmptyMessage(1)).run();
    }

    public static synchronized void changeMessage(CharSequence message) {
        if (instance != null) {
            instance.setMessage(message);
        }
    }

    public static synchronized void stopLoading() {
        if (instance != null && instance.isShowing()) {
            instance.dismiss();
        }
        instance = null;
    }

    public static boolean isLoading() {
        return instance != null && instance.isShowing();
    }
}