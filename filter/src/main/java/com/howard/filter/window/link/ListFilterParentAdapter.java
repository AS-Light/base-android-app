package com.howard.filter.window.link;


import com.howard.filter.R;
import com.howard.filter.bean.LinkCheckFilterGroup;
import com.howard.filter.window.OnFilterClickListener;
import com.howard.filter.window.SimpleListFilterAdapter;

import java.util.List;

public class ListFilterParentAdapter extends SimpleListFilterAdapter<LinkCheckFilterGroup> {

    public ListFilterParentAdapter(List<LinkCheckFilterGroup> data, OnFilterClickListener listener) {
        super(data, listener);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.link_filter_item_parent;
    }
}
