package jx.android.staff.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import jx.android.staff.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class NameEditDialog extends Dialog implements DialogInterface.OnCancelListener {

    private WeakReference<Context> mContext = new WeakReference<>(null);

    @BindView(R.id.editor_name)
    EditText mEditName;

    Unbinder unbinder;

    private String name;
    private OnActionListener mOnConfirmListener;

    public NameEditDialog(Context context, OnActionListener onConfirmListener) {
        super(context, R.style.MMFDialog);

        mContext = new WeakReference<>(context);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_name_edit, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);

        unbinder = ButterKnife.bind(this, view);
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

    @OnTextChanged(value = R.id.editor_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onEditName(Editable s) {
        name = s.toString();
    }

    @OnClick(R.id.button_confirm)
    void onClickConfirm() {
        if (mOnConfirmListener.onConfirm(name)) {
            dismiss();
        }
    }

    @OnClick(R.id.button_cancel)
    void onClickCancel() {
        dismiss();
    }

    public interface OnActionListener {
        boolean onConfirm(String name);
    }
}