package com.howard.filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.howard.filter.bean.FilterGroup;
import com.howard.filter.listener.OnFilterWindowEventListener;
import com.howard.filter.loader.PopEntityLoader;
import com.howard.filter.loader.PopEntityLoaderImp;
import com.howard.filter.window.SuperPopupWindow;

import java.util.ArrayList;

/**
 * popwindow的容器tab
 * Created by Howard on 2019.2.13.
 */
public class FilterTabLayoutA extends LinearLayout implements OnFilterWindowEventListener, OnTabStateChangeListener {

    //自定义属性,待扩展
    private double mTabTextSize = -1;
    private int mTabTextColorNormal = -1;
    private int mTabTextColorFocus = -1;
    private int mTabTextColorChecked = -1;

    private int mTabIconUpResId = R.drawable.filter_down_blue;
    private int mTabIconDownResId = R.drawable.filter_down;

    private int mTabMaxEms = -1;//字数个数超过tab_max_ems 显示...
    private int mTabMaxLines = -1;//tab item字数最大行数

    private int mTabPopAnim = R.style.PopupWindowAnimation;

    private Context mContext;

    private ArrayList<SuperPopupWindow> mPopupWindowList = new ArrayList<>();//popwindow缓存集合
    private ArrayList<FilterTabItemHolder> mTabHolderList = new ArrayList<FilterTabItemHolder>(); //筛选标签textiew集合,用于字段展示和点击事件

    private PopEntityLoader mPopEntityLoader;//筛选类型实体加载器

    public FilterTabLayoutA(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressLint("CustomViewStyleable")
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterTabLayout);
            mTabPopAnim = typedArray.getResourceId(R.styleable.FilterTabLayout_tabPopAnim, R.style.PopupWindowAnimation);

            mTabTextColorNormal = typedArray.getColor(R.styleable.FilterTabLayout_tabTextColorNormal, -1);
            mTabTextColorFocus = typedArray.getColor(R.styleable.FilterTabLayout_tabTextColorFocus, -1);
            mTabTextColorChecked = typedArray.getColor(R.styleable.FilterTabLayout_tabTextColorChecked, -1);

            mTabTextSize = typedArray.getDimension(R.styleable.FilterTabLayout_tabTextSize, -1);
            mTabMaxEms = typedArray.getInteger(R.styleable.FilterTabLayout_tabMaxEms, 4);
            mTabMaxLines = typedArray.getInteger(R.styleable.FilterTabLayout_tabMaxEms, 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
        setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * @param title     筛选标题
     * @param data      筛选数据
     * @param popupType 筛选类别
     * @return view 本身
     */
    public FilterTabLayoutA addTab(String title, FilterGroup data, int popupType) {
        FilterTabItemHolder tabHolder = new FilterTabItemHolder(getContext())
                .setColors(mTabTextColorNormal, mTabTextColorFocus, mTabTextColorChecked)
                .setIcons(mTabIconUpResId, mTabIconDownResId)
                .setTitle(title)
                .setEms(mTabMaxEms)
                .setMaxLines(mTabMaxLines)
                .setPosition(mTabHolderList.size())
                .setOnTabStateChangeListener(this);

        if (mTabTextSize != -1) {
            tabHolder.setSize((float) mTabTextSize);
        }

        //将筛选项布局加入view
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        tabHolder.getView().setLayoutParams(params);
        addView(tabHolder.getView());

        //筛选类型实体加载器
        if (mPopEntityLoader == null) {
            mPopEntityLoader = new PopEntityLoaderImp();
        }
        SuperPopupWindow mPopupWindow = (SuperPopupWindow) mPopEntityLoader.getPopEntity(getContext(), data, this, popupType);
        //mPopupWindow.setAnimationStyle(mTabPopAnim);

        //进行缓存
        mTabHolderList.add(tabHolder);
        mPopupWindowList.add(mPopupWindow);
        return this;
    }

    @Override
    public void onTabOpen(int position) {
        closeAllButPosition(position);
        if (!mPopupWindowList.get(position).isShowing()) {
            mPopupWindowList.get(position).show(this, 0);
        }
    }

    @Override
    public void onTabClose(int position) {
        mPopupWindowList.get(position).dismiss();
    }

    @Override
    public void onConfirm(SuperPopupWindow popup, FilterGroup data) {
        forceTabClose(popup);
    }

    @Override
    public void onReset(SuperPopupWindow popup) {
        forceTabClose(popup);
    }

    @Override
    public void onCancel(SuperPopupWindow popup) {
        forceTabClose(popup);
    }

    private void forceTabClose(SuperPopupWindow popup) {
        int position = mPopupWindowList.indexOf(popup);
        if (mTabHolderList.get(position).isOpen()) {
            mTabHolderList.get(position).setOpen(FilterTabItemHolder.STATE_CLOSE);
            mTabHolderList.get(position).resetStateUI();
        }
    }

    private void closeAllButPosition(int position) {
        for (int i = 0; i < mTabHolderList.size(); i++) {
            if (i != position && mTabHolderList.get(i).isOpen()) {
                mTabHolderList.get(position).setOpen(FilterTabItemHolder.STATE_CLOSE);
                mTabHolderList.get(i).resetStateUI();
            }
        }
        for (int i = 0; i < mPopupWindowList.size(); i++) {
            if (i != position && mPopupWindowList.get(i).isShowing()) {
                mPopupWindowList.get(i).dismiss();
            }
        }
    }
}
