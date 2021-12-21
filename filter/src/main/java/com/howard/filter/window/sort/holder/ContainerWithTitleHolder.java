package com.howard.filter.window.sort.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.howard.filter.R;
import com.howard.filter.bean.InputValueWithUnitFilter;
import com.howard.filter.bean.ValueInValueFilterGroup;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.bean.CheckFilterGroup;
import com.howard.filter.bean.FilterGroupWithTitle;
import com.howard.filter.bean.IntervalNumFilterGroup;
import com.howard.filter.window.sort.holder.base.BaseFilterHolder;

import java.util.ArrayList;
import java.util.List;

public class ContainerWithTitleHolder extends BaseFilterHolder<FilterGroupWithTitle> {

    private TextView mTextTitle;
    private LinearLayout mLayoutContent;
    private List<BaseFilterHolder> mHolderList = new ArrayList<>();

    public ContainerWithTitleHolder(Context context, boolean isLittle) {
        super(context, isLittle);
    }

    @Override
    protected int getLayoutResId() {
        if (isLittle) {
            return R.layout.filter_layout_container_with_title_l;
        } else {
            return R.layout.filter_layout_container_with_title;
        }
    }

    @Override
    protected void initViews(View rootView) {
        mTextTitle = rootView.findViewById(R.id.text_title);
        mLayoutContent = rootView.findViewById(R.id.layout_content);
    }

    @Override
    protected void onSetData(FilterGroupWithTitle filter) {
        mTextTitle.setText(filter.title);
        mLayoutContent.removeAllViews();

        // todo: 根据不同数据类型，添加不同类型UI
        for (BaseFilter childFilter : filter.children) {
            if (childFilter instanceof CheckFilterGroup) {
                addCheckGridHolder((CheckFilterGroup) childFilter);
            } else if (childFilter instanceof IntervalNumFilterGroup) {
                addIntervalNumHolder((IntervalNumFilterGroup) childFilter);
            } else if (childFilter instanceof InputValueWithUnitFilter) {
                addInputValueWithUnitHolder((InputValueWithUnitFilter) childFilter);
            } else if (childFilter instanceof ValueInValueFilterGroup) {
                addValueInValueFilterHolder((ValueInValueFilterGroup) childFilter);
            }
        }
    }

    @Override
    public boolean containFilter(BaseFilter filter) {
        return mData.containFilter(filter);
    }

    @Override
    public void forceRefresh(BaseFilter filter) {
        if (filter == mData) {
            for (BaseFilterHolder holder : mHolderList) {
                holder.forceRefresh(holder.getData());
            }
        } else {
            for (BaseFilterHolder holder : mHolderList) {
                if (filter == holder.getData()) {
                    holder.forceRefresh(filter);
                } else if (holder instanceof IntervalNumInputHolder && holder.containFilter(filter)) {
                    holder.forceRefresh(filter);
                } else if (holder instanceof CheckGridHolder && holder.containFilter(filter)) {
                    holder.forceRefresh(filter);
                } else if (holder instanceof ValueInValueHolder && holder.containFilter(filter)) {
                    holder.forceRefresh(filter);
                }
            }
        }
    }

    private void addCheckGridHolder(CheckFilterGroup filter) {
        addHolder(new CheckGridHolder(mContext, filter.maxChoiceCount, isLittle), filter);
    }

    private void addIntervalNumHolder(IntervalNumFilterGroup filter) {
        addHolder(new IntervalNumInputHolder(mContext, isLittle), filter);
    }

    private void addInputValueWithUnitHolder(InputValueWithUnitFilter filter) {
        addHolder(new InputValueWithUnitHolder(mContext, isLittle), filter);
    }

    private void addValueInValueFilterHolder(ValueInValueFilterGroup filter) {
        addHolder(new ValueInValueHolder(mContext, isLittle), filter);
    }

    private <T extends BaseFilter> void addHolder(BaseFilterHolder<T> holder, T data) {
        View holderView = holder.getView();
        holderView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mLayoutContent.addView(holderView);
        mHolderList.add(holder);

        holder.setNotifiableParent(mRootHolder);
        holder.setData(data);
    }
}