package com.howard.filter.window.link;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.howard.filter.R;
import com.howard.filter.bean.CheckFilter;
import com.howard.filter.bean.FilterGroup;
import com.howard.filter.bean.LinkCheckFilterGroup;
import com.howard.filter.bean.ValueCheckFilter;
import com.howard.filter.window.SuperPopupWindow;
import com.howard.filter.listener.OnFilterWindowEventListener;
import com.howard.filter.window.OnFilterClickListener;

import java.util.List;

/**
 * 左右双栏筛选PopupWindow
 *
 * @author Howard on 19/2/13.
 */
public class LinkFilterPopupWindow extends SuperPopupWindow<FilterGroup<LinkCheckFilterGroup>> {

    private RecyclerView mRecyclerParent;
    private ListFilterParentAdapter mFirstAdapter;

    private RecyclerView mRecyclerChildren;
    private ListFilterChildrenAdapter mSecondAdapter;

    private List<LinkCheckFilterGroup> mParentFilters;
    private List<ValueCheckFilter> mChildFilters;

    public LinkFilterPopupWindow(Context context, FilterGroup<LinkCheckFilterGroup> data, OnFilterWindowEventListener listener) {
        super(context, data, listener);
        initAdapter();
    }

    @Override
    public View initInnerView() {
        View mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_link, null);
        mRecyclerParent = mRootView.findViewById(R.id.rv_primary);
        mRecyclerChildren = mRootView.findViewById(R.id.rv_secondary);

        return mRootView;
    }

    public void initAdapter() {
        LinearLayoutManager firLayoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager secLayoutManager = new LinearLayoutManager(mContext);

        // todo: 一级adapter
        mParentFilters = mData.children;
        mParentFilters.get(0).checked = true;

        mFirstAdapter = new ListFilterParentAdapter(mData.children, mOnParentItemClickListener);
        mRecyclerParent.setLayoutManager(firLayoutManager);
        mRecyclerParent.setAdapter(mFirstAdapter);

        // todo: 确定一级第几个被选中，并以此设置二级adapter
        LinkCheckFilterGroup nowCheckedParentFilter = mData.children.get(0);
        mChildFilters = nowCheckedParentFilter.children;
        mChildFilters.get(0).checked = true;

        mSecondAdapter = new ListFilterChildrenAdapter(mChildFilters, mOnChildItemClickListener);
        mRecyclerChildren.setLayoutManager(secLayoutManager);
        mRecyclerChildren.setAdapter(mSecondAdapter);
    }

    private OnFilterClickListener mOnParentItemClickListener = new OnFilterClickListener<LinkCheckFilterGroup>() {
        @Override
        public void onFilterClick(LinkCheckFilterGroup filter) {
            for (LinkCheckFilterGroup parentFilter : mData.children) {
                parentFilter.checked = false;
            }
            filter.checked = true;
            mFirstAdapter.notifyDataSetChanged();

            mChildFilters = filter.children;
            mSecondAdapter.setData(mChildFilters);
            mSecondAdapter.notifyDataSetChanged();
        }
    };

    private OnFilterClickListener mOnChildItemClickListener = new OnFilterClickListener<CheckFilter>() {
        @Override
        public void onFilterClick(CheckFilter filter) {
            for (CheckFilter tempChildFilter : mChildFilters) {
                tempChildFilter.checked = false;
            }
            filter.checked = true;
            mSecondAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void show(View anchor, int paddingTop) {
        showAsDropDown(anchor);
    }
}