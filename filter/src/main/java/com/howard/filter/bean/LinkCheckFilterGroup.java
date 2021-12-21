package com.howard.filter.bean;

import com.howard.filter.bean.base.BaseFilter;

import java.util.ArrayList;
import java.util.List;

public class LinkCheckFilterGroup extends CheckFilter {
    public List<ValueCheckFilter> children;

    public LinkCheckFilterGroup(String name, boolean checked, int span) {
        super(name, checked, span);
        children = new ArrayList<>();
    }

    @Override
    public void forceNull(boolean forceFalse) {
        super.forceNull(forceFalse);
        if (forceFalse) {
            for (BaseFilter filter : children) {
                filter.forceNull(true);
            }
        }
    }

    @Override
    public void resetBackValue() {
        super.resetBackValue();
        for (ValueCheckFilter child : children) {
            if (child != null) {
                child.resetBackValue();
            }
        }
    }

    @Override
    public void backValue() {
        super.backValue();
        for (ValueCheckFilter child : children) {
            child.backValue();
        }
    }

    @Override
    public boolean isChecked() {
        for (ValueCheckFilter childFilter : children) {
            if (childFilter != null && childFilter.isChecked()) {
                return true;
            }
        }
        return false;
    }
}
