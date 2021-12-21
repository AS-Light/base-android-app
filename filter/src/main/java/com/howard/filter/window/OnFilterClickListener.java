package com.howard.filter.window;

import com.howard.filter.bean.base.BaseFilter;

public interface OnFilterClickListener<T extends BaseFilter> {

    void onFilterClick(T baseFilter);
}
