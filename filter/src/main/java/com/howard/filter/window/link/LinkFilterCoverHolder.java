package com.howard.filter.window.link;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.howard.filter.R;
import com.howard.filter.bean.CheckFilter;
import com.howard.filter.bean.FilterGroup;
import com.howard.filter.bean.LinkCheckFilterGroup;
import com.howard.filter.bean.StandAloneEventCheckFilter;
import com.howard.filter.bean.ValueCheckFilter;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.listener.OnFilterCoverHolderEventListener;
import com.howard.filter.window.OnFilterClickListener;
import com.howard.filter.window.SuperFilterCoverHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 左右双栏筛选PopupWindow
 *
 * @author Howard on 19/2/13.
 */
public class LinkFilterCoverHolder extends SuperFilterCoverHolder<FilterGroup<LinkCheckFilterGroup>> {

    private RecyclerView mRecyclerParent;
    private ListFilterParentAdapter mFirstAdapter;

    private RecyclerView mRecyclerChildren;
    private ListFilterChildrenAdapter mSecondAdapter;

    private List<LinkCheckFilterGroup> mParentFilters = new ArrayList<>();
    private List<ValueCheckFilter> mChildFilters = new ArrayList<>();
    private LinkCheckFilterGroup mParentFilterNowTarget;

    public LinkFilterCoverHolder(Context context, FilterGroup<LinkCheckFilterGroup> data, OnFilterCoverHolderEventListener listener) {
        super(context, data, listener);
        initAdapter();
    }

    @Override
    public View initInnerView() {
        @SuppressLint("InflateParams")
        View mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_link, null);
        mRecyclerParent = mRootView.findViewById(R.id.rv_primary);
        mRecyclerChildren = mRootView.findViewById(R.id.rv_secondary);
        return mRootView;
    }

    @Override
    public void forceRefresh() {
        if (mData == null) {
            return;
        }
        if (mFirstAdapter != null) {
            mParentFilters = mData.children;
            if (mParentFilters != null) {
                mFirstAdapter.setData(mParentFilters);
                mFirstAdapter.notifyDataSetChanged();
            }
        }
        if (mSecondAdapter != null) {
            if (mParentFilters == null) {
                return;
            }
            for (LinkCheckFilterGroup childGroup : mParentFilters) {
                if (childGroup.checked) {
                    mChildFilters = childGroup.children;
                }
            }
            if (!mParentFilters.isEmpty() && (mChildFilters == null || mChildFilters.isEmpty())) {
                // todo: 默认选择父中的第一个为当前父，其子为子列选项
                mParentFilterNowTarget = mParentFilters.get(0);
                mChildFilters = mParentFilters.get(0).children;
            }
            mSecondAdapter.setData(mChildFilters);
            mSecondAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        LinearLayoutManager firLayoutManager = new LinearLayoutManager(mContext);
        mFirstAdapter = new ListFilterParentAdapter(mData.children, mOnParentItemClickListener);
        mRecyclerParent.setLayoutManager(firLayoutManager);
        mRecyclerParent.setAdapter(mFirstAdapter);

        LinearLayoutManager secLayoutManager = new LinearLayoutManager(mContext);
        mSecondAdapter = new ListFilterChildrenAdapter(mChildFilters, mOnChildItemClickListener);
        mRecyclerChildren.setLayoutManager(secLayoutManager);
        mRecyclerChildren.setAdapter(mSecondAdapter);
    }

    private OnFilterClickListener mOnParentItemClickListener = new OnFilterClickListener<LinkCheckFilterGroup>() {
        @Override
        public void onFilterClick(LinkCheckFilterGroup filter) {
            if (filter.checked) {
                // todo: 父列不能取消选择
                /*filter.checked = false;
                for (CheckFilter childFilter : filter.children) {
                    childFilter.checked = false;
                }*/
            } else {
                for (LinkCheckFilterGroup parentFilter : mData.children) {
                    parentFilter.checked = false;
                    for (CheckFilter childFilter : parentFilter.children) {
                        childFilter.checked = false;
                    }
                }
                filter.checked = true;
                // todo: 没有默认选择
                /*for (CheckFilter child : filter.children) {
                    if (child.forAll) {
                        child.checked = true;
                        break;
                    }
                }*/
            }
            mFirstAdapter.notifyDataSetChanged();

            // todo: 刷新选中父项的子列
            mParentFilterNowTarget = filter;
            mChildFilters = filter.children;
            mSecondAdapter.setData(mChildFilters);
            mSecondAdapter.notifyDataSetChanged();
        }
    };

    private OnFilterClickListener mOnChildItemClickListener = new OnFilterClickListener<CheckFilter>() {
        @Override
        public void onFilterClick(CheckFilter filter) {
            if (filter instanceof StandAloneEventCheckFilter) {
                // todo: 如果是独立事件item，独立处理事件，并不和其他item发生关系
                ((StandAloneEventCheckFilter) filter).event(null);
            } else {
                if (filter.checked) {
                    // todo: 可以取消选中状态
                    filter.checked = false;
                } else {
                    // todo: “全部”和其他选项互斥
                    if (filter.forAll) {
                        for (CheckFilter tempChildFilter : mChildFilters) {
                            tempChildFilter.checked = false;
                        }
                    } else {
                        for (CheckFilter tempChildFilter : mChildFilters) {
                            if (tempChildFilter.forAll) {
                                tempChildFilter.checked = false;
                                break;
                            }
                        }
                    }
                    filter.checked = true;
                    if (filter.mutexFilters != null) {
                        for (BaseFilter mutexFilter : filter.mutexFilters) {
                            mutexFilter.checked = false;
                        }
                    }
                    /*// todo: 单选
                    for (CheckFilter tempChildFilter : mChildFilters) {
                        tempChildFilter.checked = false;
                    }
                    filter.checked = true;*/
                }

                mSecondAdapter.notifyDataSetChanged();
                // todo: 有选中或全部取消选中，影响父选项变更
                /*boolean isChildrenAllCancel = true;
                for (CheckFilter childFilter : mChildFilters) {
                    if (childFilter.checked) {
                        isChildrenAllCancel = false;
                        break;
                    }
                }
                mParentFilterNowTarget.checked = !isChildrenAllCancel;
                mFirstAdapter.notifyDataSetChanged();*/
            }
        }
    };
}