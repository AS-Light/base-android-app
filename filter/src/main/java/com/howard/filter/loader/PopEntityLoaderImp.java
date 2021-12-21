package com.howard.filter.loader;


import android.content.Context;
import android.widget.PopupWindow;

import com.howard.filter.bean.FilterGroup;
import com.howard.filter.bean.FilterGroupWithTitle;
import com.howard.filter.bean.LinkCheckFilterGroup;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.listener.OnFilterWindowEventListener;
import com.howard.filter.window.link.LinkFilterPopupWindow;
import com.howard.filter.window.sort.SortPopupWindow;
import com.howard.filter.FilterConfig;

/**
 * 由筛选器类型 建立实体 的loader
 * Created by chenchangjun on 17/6/28.
 */

public class PopEntityLoaderImp implements PopEntityLoader {

    @Override
    public PopupWindow getPopEntity(Context context, BaseFilter data, OnFilterWindowEventListener filterSetListener, int filterType) {
        PopupWindow popupWindow = null;

        switch (filterType) {
            case FilterConfig.TYPE_POPUP_LINKED:
                popupWindow = new LinkFilterPopupWindow(context, (FilterGroup<LinkCheckFilterGroup>) data, filterSetListener);
                break;
            case FilterConfig.TYPE_POPUP_SORT:
                popupWindow = new SortPopupWindow(context, (FilterGroup<FilterGroupWithTitle>) data, filterSetListener);
                break;
        }
        return popupWindow;
    }
}
