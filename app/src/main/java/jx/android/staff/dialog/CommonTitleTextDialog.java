package jx.android.staff.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
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

public class CommonTitleTextDialog extends Dialog implements DialogInterface.OnCancelListener {

    private WeakReference<Context> mContext = new WeakReference<>(null);

    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_info)
    TextView mTextInfo;
    @BindView(R.id.button_cancel)
    TextView mButtonCancel;
    @BindView(R.id.button_confirm)
    TextView mButtonConfirm;

    Unbinder unbinder;

    private String name;
    private OnActionListener mOnConfirmListener;

    public CommonTitleTextDialog(Context context, String title, String info, String cancelStr, String confirmStr, boolean showCancel, OnActionListener onConfirmListener) {
        super(context, R.style.MMFDialog);

        mContext = new WeakReference<>(context);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common_title_text, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);

        unbinder = ButterKnife.bind(this, view);

        mTextTitle.setText(title);
        mTextInfo.setText(Html.fromHtml(info));
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
        // ???????????????????????????Dialog???????????????????????????????????????????????????
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
        dismiss();
    }

    public interface OnActionListener {
        void onConfirm();

        void onCancel();
    }
}