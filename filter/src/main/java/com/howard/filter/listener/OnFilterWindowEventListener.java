package com.howard.filter.listener;


import com.howard.filter.bean.FilterGroup;
import com.howard.filter.window.SuperPopupWindow;

/**
 * 各个filter和popstabview的交互接口
 * Created by chenchangjun on 17/7/6.
 */

public interface OnFilterWindowEventListener {

    void onConfirm(SuperPopupWindow popup, FilterGroup data);

    void onReset(SuperPopupWindow popup);

    void onCancel(SuperPopupWindow popup);
}
