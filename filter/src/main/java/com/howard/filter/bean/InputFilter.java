package com.howard.filter.bean;

import android.text.TextUtils;

import com.howard.filter.bean.base.BaseFilter;

public class InputFilter extends BaseFilter {
    public String value = "";

    public String lastValue = "";

    public InputFilter(String value) {
        this.value = value;
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
