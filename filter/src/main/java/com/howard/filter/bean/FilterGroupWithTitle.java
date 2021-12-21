package com.howard.filter.bean;

import com.howard.filter.bean.base.BaseFilter;

public class FilterGroupWithTitle extends FilterGroup<BaseFilter> {
    public String title;

    public FilterGroupWithTitle(String title) {
        this.title = title;
    }

    @Override
    public void forceNull(boolean falseForce) {
        if (falseForce) {
            for (BaseFilter filter : children) {
                filter.forceNull(true);
            }
        }
    }

    @Override
    public boolean isChecked() {
        for (BaseFilter child : children) {
            if (child.isChecked()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containFilter(BaseFilter filter) {
        for (BaseFilter childFilter : children) {
            if (childFilter instanceof CheckFilterGroup) {
                if (((CheckFilterGroup) childFilter).containFilter(filter)) {
                    return true;
                }
            } else if (childFilter instanceof IntervalNumFilterGroup) {
                if (((IntervalNumFilterGroup) childFilter).containFilter(filter)) {
                    return true;
                }
            }
        }
        return false;
    }
}
