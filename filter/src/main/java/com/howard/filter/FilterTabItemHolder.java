package com.howard.filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class FilterTabItemHolder {

    final static boolean STATE_OPEN = true;
    final static boolean STATE_CLOSE = false;

    private Context mContext;

    private View mRootLayout;
    private TextView mTextTitle;

    private int mColorClose, mColorOpen, mColorChecked;
    private int mIconUpResId, mIconDownResId;

    private boolean isOpen = STATE_CLOSE;
    private boolean isPanelChecked = false;

    private int position = 0;

    private OnTabStateChangeListener mOnTabStateChangeListener;

    @SuppressLint("InflateParams")
    FilterTabItemHolder(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mRootLayout = inflater.inflate(R.layout.tab_view, null);
        mTextTitle = mRootLayout.findViewById(R.id.text_tab);
    }

    public FilterTabItemHolder setTitle(String title) {
        mTextTitle.setText(title);
        return this;
    }

    FilterTabItemHolder setColors(int closeColor, int openColor, int checkedColor) {
        mColorClose = closeColor;
        mColorOpen = openColor;
        mColorChecked = checkedColor;
        return this;
    }

    FilterTabItemHolder setIcons(int upIconResId, int downIconResId) {
        mIconUpResId = upIconResId;
        mIconDownResId = downIconResId;
        return this;
    }

    FilterTabItemHolder setEms(int ems) {
        mTextTitle.setEms(ems);
        return this;
    }

    FilterTabItemHolder setMaxLines(int lines) {
        mTextTitle.setMaxLines(lines);
        return this;
    }

    public FilterTabItemHolder setSize(float size) {
        mTextTitle.setTextSize(size);
        return this;
    }

    public FilterTabItemHolder setPosition(int position) {
        this.position = position;
        return this;
    }

    FilterTabItemHolder setOnTabStateChangeListener(final OnTabStateChangeListener listener) {
        mOnTabStateChangeListener = listener;
        mTextTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilterTabClick();
            }
        });
        return this;
    }

    public void onFilterTabClick() {
        changeStateInner();
        resetStateUI();
        if (isOpen()) {
            if (mOnTabStateChangeListener != null) {
                mOnTabStateChangeListener.onTabOpen(position);
            }
        } else {
            if (mOnTabStateChangeListener != null) {
                mOnTabStateChangeListener.onTabClose(position);
            }
        }
    }

    public View getView() {
        return mRootLayout;
    }

    private void changeStateInner() {
        setOpen(!isOpen());
    }

    void setOpen(boolean open) {
        isOpen = open;
    }

    boolean isOpen() {
        return isOpen;
    }

    void setPanelChecked(boolean checked) {
        isPanelChecked = checked;
    }

    void resetStateUI() {
        if (isOpen) {
            mTextTitle.setTextColor(mColorOpen);
            mTextTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(mIconUpResId), null);
        } else {
            if (isPanelChecked) {
                mTextTitle.setTextColor(mColorChecked);
                mTextTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(mIconDownResId), null);
            } else {
                mTextTitle.setTextColor(mColorClose);
                mTextTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(mIconDownResId), null);
            }
        }

    }
}
