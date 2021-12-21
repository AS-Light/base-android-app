package com.howard.filter.bean;

public class ValueCheckFilter<T> extends CheckFilter {
    public T value;

    public ValueCheckFilter(String name, boolean checked, int span, T value) {
        super(name, checked, span);
        this.value = value;
    }
}
