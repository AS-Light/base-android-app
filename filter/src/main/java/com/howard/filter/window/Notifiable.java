package com.howard.filter.window;

import com.howard.filter.bean.base.BaseFilter;

public interface Notifiable {

    void notifyFilterChanged(BaseFilter filter);
}
