package com.howard.filter.window;


import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.howard.filter.R;
import com.howard.filter.bean.FilterGroup;
import com.howard.filter.listener.OnFilterWindowEventListener;

/**
 * Created by Howard on 19/2/13.
 */

public abstract class SuperPopupWindow<T extends FilterGroup> extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {

    protected Context mContext;
    private View mRootView;

    private LinearLayout mLayoutContent;
    private TextView mButtonReset;
    private TextView mButtonConfirm;

    protected OnFilterWindowEventListener mOnFilterWindowEventListener;

    protected T mData;

    private boolean isDismissWithEvent = false;

    public SuperPopupWindow(Context context, T data, OnFilterWindowEventListener onFilterWindowEventListener) {
        mContext = context;
        mData = data;
        mOnFilterWindowEventListener = onFilterWindowEventListener;
        mRootView = initView(mContext);
        setContentView(mRootView);
        initCommonContentView();
    }

    public View initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.popup_filter_container, null);

        mLayoutContent = rootView.findViewById(R.id.layout_content);
        mLayoutContent.removeAllViews();

        View innerView = initInnerView();
        innerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLayoutContent.addView(innerView);

        mButtonReset = rootView.findViewById(R.id.button_reset);
        mButtonConfirm = rootView.findViewById(R.id.button_confirm);

        mButtonReset.setOnClickListener(this);
        mButtonConfirm.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_confirm) {
            mOnFilterWindowEventListener.onConfirm(this, mData);
        } else if (id == R.id.button_reset) {
            mOnFilterWindowEventListener.onReset(this);
        } else {
            mOnFilterWindowEventListener.onCancel(this);
        }
        isDismissWithEvent = true;
        dismiss();
    }

    public abstract View initInnerView();

    protected void initCommonContentView() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //setTouchable(true);
        mRootView.setFocusable(true);
        //setOutsideTouchable(false);
        //setBackgroundDrawable(new ColorDrawable());
        //mRootView.setOnClickListener(this);
        setOnDismissListener(this);
    }

    /**
     * 如果有需要,子类会重写该方法,
     *
     * @param anchor
     * @param paddingTop
     */
    public void show(View anchor, int paddingTop) {
        showAsDropDown(anchor);
    }

    /**
     * 适配Android7.0
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onDismiss() {
        if (!isDismissWithEvent) {
            mOnFilterWindowEventListener.onCancel(this);
        }
        isDismissWithEvent = false;
    }

    public void setData(T data) {
        mData = data;
    }

    public T getData() {
        return mData;
    }
}
