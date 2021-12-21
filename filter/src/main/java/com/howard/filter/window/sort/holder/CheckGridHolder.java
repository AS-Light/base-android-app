package com.howard.filter.window.sort.holder;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.howard.filter.R;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.bean.CheckFilter;
import com.howard.filter.bean.CheckFilterGroup;
import com.howard.filter.bean.CheckInputFilter;
import com.howard.filter.listener.OnHolderClickedListener;
import com.howard.filter.window.sort.holder.base.BaseFilterHolder;

public class CheckGridHolder extends BaseFilterHolder<CheckFilterGroup> implements OnHolderClickedListener {

    private RecyclerView mRecycler;
    private CheckGridAdapter mAdapter;

    private CheckFilterGroup mData;
    private int mMaxChoiceCount = 1;

    CheckGridHolder(Context context, int maxChoiceCount, boolean isLittle) {
        super(context, isLittle);
        mMaxChoiceCount = maxChoiceCount;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fitler_layout_check_grid;
    }

    @Override
    protected void initViews(View rootView) {
        mRecycler = (RecyclerView) mRootView;
        mRecycler.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 12);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (mData != null && mData.children.size() > i) {
                    return mData.children.get(i).span;
                } else {
                    return 12;
                }
            }
        });
        mRecycler.setLayoutManager(gridLayoutManager);

        mAdapter = new CheckGridAdapter(isLittle);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void onSetData(CheckFilterGroup filterGroup) {
        mData = filterGroup;
        mAdapter.setData(mData.children, this);
    }

    @Override
    public boolean containFilter(BaseFilter filter) {
        return mData.containFilter(filter);
    }


    @Override
    public void forceRefresh(BaseFilter filter) {
        try {
            if (filter instanceof CheckInputFilter) {
                if (!filter.checked) {
                    mAdapter.notifyItemChanged(mData.children.indexOf(filter));
                }
            } else {
                mAdapter.notifyItemChanged(mData.children.indexOf(filter));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onItemClick(int position) {
        CheckFilter itemFilter = mData.children.get(position);

        if (itemFilter.checked) {
            itemFilter.checked = false;
            mRootHolder.notifyFilterChanged(itemFilter);
        } else {
            itemFilter.checked = true;
            if (!(itemFilter instanceof CheckInputFilter)) {
                mRootHolder.notifyFilterChanged(itemFilter);
            }

            if (itemFilter.mutexFilters != null) {
                for (BaseFilter filter : itemFilter.mutexFilters) {
                    if (filter instanceof CheckFilter) {
                        filter.checked = false;
                        if (filter instanceof CheckInputFilter) {
                            ((CheckInputFilter) filter).value = "";
                        }
                        mRootHolder.notifyFilterChanged(filter);
                    } else {
                        filter.forceNull(true);
                        mRootHolder.notifyFilterChanged(filter);
                    }
                }
            }
        }
    }
}
