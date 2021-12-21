package com.howard.filter.bean;

import com.howard.filter.bean.base.BaseFilter;

public class ValueInValueFilterGroup extends BaseFilter {
    public InputFilter valueFilter;
    public InputFilter fullValueFilter;

    public ValueInValueFilterGroup(InputFilter valueFilter, InputFilter fullValueFilter) {
        this.valueFilter = valueFilter;
        this.fullValueFilter = fullValueFilter;
    }

    @Override
    public void forceNull(boolean forceNull) {
        if (forceNull) {
            valueFilter.value = "";
            fullValueFilter.value = "";
        }
    }

    @Override
    public void resetBackValue() {
        super.resetBackValue();
        valueFilter.resetBackValue();
        fullValueFilter.resetBackValue();
    }

    @Override
    public void backValue() {
        super.backValue();
        valueFilter.backValue();
        fullValueFilter.backValue();
    }

    @Override
    public boolean isChecked() {
        return valueFilter.isChecked() || fullValueFilter.isChecked();
    }

    public boolean containFilter(BaseFilter filter) {
        return valueFilter == filter || fullValueFilter == filter;
    }
}