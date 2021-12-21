package com.howard.filter.loader;


import android.content.Context;

import com.howard.filter.bean.FilterGroup;
import com.howard.filter.bean.FilterGroupWithTitle;
import com.howard.filter.bean.LinkCheckFilterGroup;
import com.howard.filter.bean.base.BaseFilter;
import com.howard.filter.listener.OnFilterCoverHolderEventListener;
import com.howard.filter.window.SuperFilterCoverHolder;
import com.howard.filter.window.link.LinkFilterCoverHolder;
import com.howard.filter.window.sort.SortFilterCoverHolder;
import com.howard.filter.FilterConfig;

/**
 * 由筛选器类型 建立实体 的loader
 * Created by chenchangjun on 17/6/28.
 */

public class FilterCoverLoaderImp implements FilterCoverLoader {

    @Override
    public SuperFilterCoverHolder getPopEntity(Context context, BaseFilter data, OnFilterCoverHolderEventListener filterSetListener, int filterType) {
        SuperFilterCoverHolder filterCoverHolder = null;

        switch (filterType) {
            case FilterConfig.TYPE_POPUP_LINKED:
                filterCoverHolder = new LinkFilterCoverHolder(context, (FilterGroup<LinkCheckFilterGroup>) data, filterSetListener);
                break;
            case FilterConfig.TYPE_POPUP_SORT:
                filterCoverHolder = new SortFilterCoverHolder(context, (FilterGroup<FilterGroupWithTitle>) data, filterSetListener);
                break;
        }
        return filterCoverHolder;
    }
}
