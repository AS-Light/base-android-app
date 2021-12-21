package com.howard.filter.bean;

import com.howard.filter.bean.base.BaseFilter;

public class CheckFilter extends BaseFilter {
    public int span = 1;
    public String name = "";
    public boolean forAll = false;

    public CheckFilter(String name, boolean checked, int span) {
        this.name = name;
        this.checked = checked;
        this.span = span;
    }

    @Override
    public void forceNull(boolean falseFalse) {
        if (falseFalse) {
            checked = false;
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

}
