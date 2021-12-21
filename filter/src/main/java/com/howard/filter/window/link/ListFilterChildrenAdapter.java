package com.howard.filter.window.link;


import com.howard.filter.R;
import com.howard.filter.bean.ValueCheckFilter;

import com.howard.filter.window.OnFilterClickListener;
import com.howard.filter.window.SimpleListFilterAdapter;

import java.util.List;

public class ListFilterChildrenAdapter extends SimpleListFilterAdapter<ValueCheckFilter> {

    public ListFilterChildrenAdapter(List<ValueCheckFilter> data, OnFilterClickListener listener) {
        super(data, listener);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.link_filter_item_child;
    }
}
