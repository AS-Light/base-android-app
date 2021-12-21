package com.howard.filter.bean;

public class StringValueCheckFilter extends ValueCheckFilter<String> {
    public StringValueCheckFilter(String name, boolean checked, int span, String value) {
        super(name, checked, span, value);
    }
}
