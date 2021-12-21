package com.howard.filter.bean;

public class ShotNameValueCheckFilter extends ValueCheckFilter<String> {
    public String shotName;

    public ShotNameValueCheckFilter(String name, boolean checked, int span, String value, String shotName) {
        super(name, checked, span, value);
        this.shotName = shotName;
    }
}
