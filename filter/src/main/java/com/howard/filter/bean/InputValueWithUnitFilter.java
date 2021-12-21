package com.howard.filter.bean;

import android.text.TextUtils;

import com.howard.filter.bean.base.BaseFilter;

public class InputValueWithUnitFilter extends BaseFilter {
    public String value = "";
    public String lastValue = "";

    public String unit = "";

    public InputValueWithUnitFilter(String value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public void forceNull(boolean forceFalse) {
        if (forceFalse) {
            value = "";
        }
    }

    @Override
    public void resetBackValue() {
        super.resetBackValue();
        lastValue = value;
    }

    @Override
    public void backValue() {
        super.backValue();
        value = lastValue;
    }

    @Override
    public boolean isChecked() {
        return !TextUtils.isEmpty(value);
    }
}