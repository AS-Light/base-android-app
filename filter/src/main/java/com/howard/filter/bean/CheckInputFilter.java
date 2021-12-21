package com.howard.filter.bean;

public class CheckInputFilter extends CheckFilter {
    public String value;

    public CheckInputFilter(String name, boolean checked, int span, String value) {
        super(name, checked, span);
        this.value = value;
    }

    @Override
    public void forceNull(boolean falseFalse) {
        super.forceNull(falseFalse);
    }
}
