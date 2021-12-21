package com.howard.filter.bean;

import com.howard.filter.bean.base.BaseFilter;

import java.util.ArrayList;
import java.util.List;

public abstract class FilterGroup<T extends BaseFilter> extends BaseFilter {
    public List<T> children;

    public FilterGroup() {
        children = new ArrayList<>();
    }

    @Override
    public void resetBackValue() {
        super.resetBackValue();
        for (T child : children) {
            child.resetBackValue();
        }
    }

    @Override
    public void backValue() {
        super.backValue();
        for (T child : children) {
            child.backValue();
        }
    }

    public abstract boolean containFilter(BaseFilter filter);
}
