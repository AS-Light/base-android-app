package jx.android.staff.binding.handler.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import jx.android.staff.binding.vm.base.BaseViewModel;
import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.binding.handler.delegate.base.Delegate;

public class BaseHandler<VM extends BaseViewModel, D extends Delegate> {

    public Context mContext;
    public VM mViewModel;
    public D mDelegate;

    protected BaseHandler(Context context) {
        mContext = context;
    }

    protected BaseHandler(Context context, VM viewModel) {
        mContext = context;
        mViewModel = viewModel;
    }

    protected BaseHandler(Context context, VM viewModel, D delegate) {
        mContext = context;
        mViewModel = viewModel;
        mDelegate = delegate;
    }

    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
    }

    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    protected void startActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(mContext, clazz);
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(bundle);
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    protected void showWaitDialog() {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).showWaitDialog();
        }
    }

    protected void hideWaitDialog() {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).hideWaitDialog();
        }
    }

    protected void finish() {
        ((Activity) mContext).finish();
    }

    public void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (((Activity) mContext).getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (((Activity) mContext).getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
