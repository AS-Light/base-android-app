package jx.android.staff.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import jx.android.staff.R;
import jx.android.staff.databinding.DialogUpdateBinding;

import java.lang.ref.WeakReference;

public class UpdateDialog extends Dialog implements DialogInterface.OnCancelListener {

    private WeakReference<Context> mContext = new WeakReference<>(null);


    private OnActionListener mOnConfirmListener;

    public UpdateDialog(Context context, Spanned title, String confirmStr, boolean showCancel, OnActionListener onConfirmListener) {
        super(context, R.style.MMFDialog);

        mContext = new WeakReference<>(context);

        DialogUpdateBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_update, null, false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(binding.getRoot(), lp);
        setCanceledOnTouchOutside(true);

        binding.buttonConfirm.setText(confirmStr);
        binding.textTitle.setText(title);
        setOnCancelListener(this);
        mOnConfirmListener = onConfirmListener;

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickConfirm();
            }
        });
    }


    public interface OnActionListener {
        void onConfirm();

        void onCancel();
    }


    public void onClickConfirm() {
        if (mOnConfirmListener != null) {
            mOnConfirmListener.onConfirm();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等
        Context context = mContext.get();
        if (context != null) {
            Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
        }
    }

}