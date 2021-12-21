package com.howard.filter.window;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.howard.filter.R;
import com.howard.filter.bean.FilterGroup;
import com.howard.filter.listener.OnFilterCoverHolderEventListener;

/**
 * Created by Howard on 19/2/13.
 */

public abstract class SuperFilterCoverHolder<T extends FilterGroup> implements View.OnClickListener {

    protected Context mContext;
    private View mRootView;

    private FrameLayout mLayoutContent;
    private TextView mButtonReset;
    private TextView mButtonConfirm;

    protected OnFilterCoverHolderEventListener mOnFilterCoverHolderEventListener;

    protected T mData;

    private boolean isDismissWithEvent = false;

    public SuperFilterCoverHolder(Context context, T data, OnFilterCoverHolderEventListener onFilterCoverHolderEventListener) {
        mContext = context;
        mData = data;
        mOnFilterCoverHolderEventListener = onFilterCoverHolderEventListener;
        mRootView = initView(mContext);
    }

    public View getView() {
        return mRootView;
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

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mOnFilterCoverHolderEventListener.onCancel(SuperFilterCoverHolder.this);
                return true;
            }
        });

        mLayoutContent.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
        mButtonConfirm.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_confirm) {//
            mOnFilterCoverHolderEventListener.onConfirm(this, mData);
        } else if (id == R.id.button_reset) {//
            mOnFilterCoverHolderEventListener.onReset(this, mData);
        } else {
            mOnFilterCoverHolderEventListener.onCancel(this);
        }
        isDismissWithEvent = true;
    }

    public abstract View initInnerView();

    public void setData(T data) {
        mData = data;
    }

    public T getData() {
        return mData;
    }

    public void resetBackValue() {
        if (mData != null) {
            mData.resetBackValue();
        }
    }

    public void backValue() {
        if (mData != null) {
            mData.backValue();
        }
    }

    public abstract void forceRefresh();
}
