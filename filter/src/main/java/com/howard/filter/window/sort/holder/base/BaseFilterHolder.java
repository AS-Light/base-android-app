package com.howard.filter.window.sort.holder.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.window.Notifiable;

public abstract class BaseFilterHolder<T extends BaseFilter> {

    protected Context mContext;

    protected View mRootView;

    protected T mData;

    protected Notifiable mRootHolder;

    protected boolean isLittle = false;

    protected BaseFilterHolder(Context context, boolean isLittle) {
        mContext = context;
        this.isLittle = isLittle;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mRootView = inflater.inflate(getLayoutResId(), null);
        initViews(mRootView);
    }

    public View getView() {
        return mRootView;
    }

    public void setData(T filter) {
        mData = filter;
        onSetData(filter);
    }

    public T getData() {
        return mData;
    }

    public abstract boolean containFilter(BaseFilter filter);

    protected abstract int getLayoutResId();

    protected abstract void initViews(View rootView);

    protected abstract void onSetData(T baseFilter);

    public abstract void forceRefresh(BaseFilter filter);

    public void setNotifiableParent(Notifiable rootHolder) {
        mRootHolder = rootHolder;
    }
}
