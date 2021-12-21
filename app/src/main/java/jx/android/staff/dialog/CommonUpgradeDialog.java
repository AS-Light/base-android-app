package jx.android.staff.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import jx.android.staff.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CommonUpgradeDialog extends Dialog {

    private WeakReference<Context> mContext = new WeakReference<>(null);

    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @BindView(R.id.text_count)
    TextView mTextCount;
    @BindView(R.id.button_cancel)
    TextView mButtonCancel;

    Unbinder unbinder;

    int mMax;
    int mProgress = 0;

    private OnActionListener mOnActionListener;

    public CommonUpgradeDialog(Context context, String title, OnActionListener onActionListener) {
        super(context, R.style.MMFDialog);

        mContext = new WeakReference<>(context);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common_upgrade, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);

        unbinder = ButterKnife.bind(this, view);
        mTextTitle.setText(title);

        mOnActionListener = onActionListener;
    }

    @OnClick(R.id.button_cancel)
    void onClickCancel() {
        if (mOnActionListener != null) {
            mOnActionListener.onCancel();
        }
        dismiss();
    }

    public interface OnActionListener {
        void onCancel();
    }

    @SuppressLint("DefaultLocale")
    public void init(int max) {
        mMax = max;
        mTextCount.setText(String.format("%d/%d", 0, mMax));
        mProgressBar.setMax(max);
    }

    @SuppressLint("DefaultLocale")
    public void setProgress(final int progress) {
        mProgress = progress;
        if (mTextCount == null) {
            return;
        }
        mTextCount.setText(String.format("%d/%d", mProgress, mMax));
        mProgressBar.post(() -> {
            if (mProgressBar == null) {
                return;
            }
            mProgressBar.setProgress(progress);
            mProgressBar.invalidate();
        });

    }
}