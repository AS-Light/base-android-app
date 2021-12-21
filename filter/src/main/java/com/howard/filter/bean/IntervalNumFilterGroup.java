package com.howard.filter.bean;

import com.howard.filter.bean.base.BaseFilter;

public class IntervalNumFilterGroup extends BaseFilter {
    public InputFilter startFilter;
    public InputFilter endFilter;

    public IntervalNumFilterGroup(InputFilter startFilter, InputFilter endFilter) {
        this.startFilter = startFilter;
        this.endFilter = endFilter;
    }

    @Override
    public void forceNull(boolean forceNull) {
        if (forceNull) {
            startFilter.value = "";
            endFilter.value = "";
        }
    }

    @Override
    public void resetBackValue() {
        super.resetBackValue();
        startFilter.resetBackValue();
        endFilter.resetBackValue();
    }

    @Override
    public void backValue() {
        super.backValue();
        startFilter.backValue();
        endFilter.backValue();
    }

    @Override
    public boolean isChecked() {
        return startFilter.isChecked() || endFilter.isChecked();
    }

    public boolean containFilter(BaseFilter filter) {
        return startFilter == filter || endFilter == filter;
    }
}
