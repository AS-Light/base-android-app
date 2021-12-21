package com.howard.filter.window.sort;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.howard.filter.R;
import com.howard.filter.bean.FilterGroup;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.window.sort.holder.ContainerWithTitleHolder;
import com.howard.filter.bean.FilterGroupWithTitle;
import com.howard.filter.listener.OnFilterCoverHolderEventListener;
import com.howard.filter.window.Notifiable;
import com.howard.filter.window.SuperFilterCoverHolder;
import com.howard.filter.window.sort.holder.base.BaseFilterHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用复合单选PopupWindow
 *
 * @author  by ccj on 17/6/22
 */
public class SortFilterCoverHolder extends SuperFilterCoverHolder<FilterGroup<FilterGroupWithTitle>> implements Notifiable {

    private LinearLayout mLayoutContentInner;
    private List<ContainerWithTitleHolder> mHolderList;

    public SortFilterCoverHolder(Context context, FilterGroup<FilterGroupWithTitle> data, OnFilterCoverHolderEventListener listener) {
        super(context, data, listener);
    }

    @Override
    public View initInnerView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_sort, null);
        mLayoutContentInner = rootView.findViewById(R.id.layout_content);
        showData();
        return rootView;
    }

    @Override
    public void forceRefresh() {
        for (BaseFilterHolder holder : mHolderList) {
            holder.forceRefresh(holder.getData());
        }
    }

    private void showData() {
        mHolderList = new ArrayList<>();
        for (FilterGroupWithTitle titleFilterGroup : mData.children) {
            ContainerWithTitleHolder holder = new ContainerWithTitleHolder(mContext, false);

            View holderView = holder.getView();
            holderView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.setNotifiableParent(this);

            mLayoutContentInner.addView(holderView);
            mHolderList.add(holder);

            holder.setData(titleFilterGroup);
        }
    }

    @Override
    public void notifyFilterChanged(BaseFilter filter) {
        for (BaseFilterHolder holder : mHolderList) {
            holder.forceRefresh(filter);
        }
    }
}
