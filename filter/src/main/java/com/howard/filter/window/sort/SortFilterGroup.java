package com.howard.filter.window.sort;

import com.howard.filter.bean.FilterGroup;
import com.howard.filter.bean.base.BaseFilter;

public class SortFilterGroup<T extends BaseFilter> extends FilterGroup<T> {
    @Override
    public void forceNull(boolean forceFalse) {

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
        return false;
    }
}
