package jx.android.staff.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jx.android.staff.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CommonTitleDialog extends Dialog implements DialogInterface.OnCancelListener {

    private WeakReference<Context> mContext = new WeakReference<>(null);

    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.button_cancel)
    TextView mButtonCancel;
    @BindView(R.id.button_confirm)
    TextView mButtonConfirm;

    Unbinder unbinder;

    private OnActionListener mOnConfirmListener;

    public CommonTitleDialog(Context context, String title, String cancelStr, String confirmStr, boolean showCancel, OnActionListener onConfirmListener) {
        super(context, R.style.MMFDialog);

        mContext = new WeakReference<>(context);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common_title, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);

        unbinder = ButterKnife.bind(this, view);

        mTextTitle.setText(title);
        mButtonConfirm.setText(confirmStr);
        if (!showCancel) {
            mButtonCancel.setVisibility(View.GONE);
            mButtonConfirm.setBackgroundResource(R.drawable.shape_bottom_left_right_radius_blue);
        } else {
            mButtonCancel.setText(cancelStr);
        }
        setOnCancelListener(this);

        mOnConfirmListener = onConfirmListener;
    }

    @Override
    public void dismiss() {
        unbinder.unbind();
        super.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等
        Context context = mContext.get();
        if (context != null) {
//            Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.button_confirm)
    void onClickConfirm() {
        if (mOnConfirmListener != null) {
            mOnConfirmListener.onConfirm();
        }
        dismiss();
    }

    @OnClick(R.id.button_cancel)
    void onClickCancel() {
        if (mOnConfirmListener != null) {
            mOnConfirmListener.onCancel();
        }
        dismiss();
    }

    public interface OnActionListener {
        void onConfirm();
        void onCancel();
    }
}