package com.howard.filter.bean;

import android.os.Bundle;

public abstract class StandAloneEventCheckFilter extends ValueCheckFilter<String> {

    public StandAloneEventCheckFilter(String name, boolean checked, int span, String value) {
        super(name, checked, span, value);
    }

    public abstract void event(Bundle bundle);
}
