package com.howard.filter.listener;


import com.howard.filter.bean.FilterGroup;
import com.howard.filter.window.SuperFilterCoverHolder;

/**
 * 各个filter和popstabview的交互接口
 * Created by chenchangjun on 17/7/6.
 */

public interface OnFilterCoverHolderEventListener {

    boolean onConfirm(SuperFilterCoverHolder holder, FilterGroup data);

    void onReset(SuperFilterCoverHolder holder, FilterGroup data);

    void onCancel(SuperFilterCoverHolder holder);
}
