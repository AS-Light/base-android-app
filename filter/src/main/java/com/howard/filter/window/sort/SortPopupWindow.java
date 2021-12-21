package com.howard.filter.window.sort;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.howard.filter.R;
import com.howard.filter.bean.FilterGroup;
import com.howard.filter.window.sort.holder.ContainerWithTitleHolder;
import com.howard.filter.window.SuperPopupWindow;
import com.howard.filter.bean.FilterGroupWithTitle;
import com.howard.filter.listener.OnFilterWindowEventListener;

/**
 * 通用复合单选PopupWindow
 *
 * @author  by ccj on 17/6/22
 */
public class SortPopupWindow extends SuperPopupWindow<FilterGroup<FilterGroupWithTitle>> {

    private LinearLayout mLayoutContentInner;

    public SortPopupWindow(Context context, FilterGroup<FilterGroupWithTitle> data, OnFilterWindowEventListener listener) {
        super(context, data, listener);
    }

    @Override
    public View initInnerView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_sort, null);
        mLayoutContentInner = rootView.findViewById(R.id.layout_content);
        showData();
        return rootView;
    }

    private void showData() {
        for (FilterGroupWithTitle titleFilterGroup : mData.children) {
            ContainerWithTitleHolder holder = new ContainerWithTitleHolder(mContext, false);

            View holderView = holder.getView();
            holderView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mLayoutContentInner.addView(holderView);

            holder.setData(titleFilterGroup);
        }
    }

    @Override
    public void show(View anchor, int paddingTop) {
        showAsDropDown(anchor);
    }

}
