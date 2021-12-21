package com.howard.filter.bean.base;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFilter implements ValueBackable {
    public String key = "";
    public boolean checked = false;

    public boolean lastChecked = false;

    @Override
    public void resetBackValue() {
        lastChecked = checked;
    }

    @Override
    public void backValue() {
        checked = lastChecked;
    }

    public List<BaseFilter> mutexFilters = new ArrayList<>();
    public abstract void forceNull(boolean forceFalse);
    public abstract boolean isChecked();
}
