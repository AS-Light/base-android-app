package com.howard.filter.bean;

public class IntervalCheckFilter extends CheckFilter {

    public String startValue;
    public String endValue;

    public IntervalCheckFilter(String name, boolean checked, int span, String startValue, String endValue) {
        super(name, checked, span);
        this.startValue = startValue;
        this.endValue = endValue;
    }
}
