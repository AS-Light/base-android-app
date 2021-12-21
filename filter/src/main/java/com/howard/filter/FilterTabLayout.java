package com.howard.filter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.howard.filter.bean.FilterGroup;
import com.howard.filter.listener.OnFilterCoverHolderEventListener;
import com.howard.filter.loader.FilterCoverLoader;
import com.howard.filter.loader.FilterCoverLoaderImp;
import com.howard.filter.window.SuperFilterCoverHolder;

import java.util.ArrayList;

/**
 * popwindow的容器tab
 * Created by Howard on 2019.2.13.
 */
public class FilterTabLayout extends LinearLayout implements OnFilterCoverHolderEventListener, OnTabStateChangeListener {

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
    private ViewGroup mParentCoverView;

    private ArrayList<SuperFilterCoverHolder> mFilterCoverList = new ArrayList<>();
    private ArrayList<FilterTabItemHolder> mTabHolderList = new ArrayList<>();

    private FilterCoverLoader mPopEntityLoader;//筛选类型实体加载器

    private OnFilterCoverHolderEventListener mOnFilterCoverHolderEventListener;
    private OnFilterShowListener mOnFilterShowListener;

    public FilterTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterTabLayout);
            mTabPopAnim = typedArray.getResourceId(R.styleable.FilterTabLayout_tabPopAnim, R.style.PopupWindowAnimation);

            mTabTextColorNormal = typedArray.getColor(R.styleable.FilterTabLayout_tabTextColorNormal, -1);
            mTabTextColorFocus = typedArray.getColor(R.styleable.FilterTabLayout_tabTextColorFocus, -1);
            mTabTextColorChecked = typedArray.getColor(R.styleable.FilterTabLayout_tabTextColorChecked, -1);

            mTabTextSize = typedArray.getDimensionPixelOffset(R.styleable.FilterTabLayout_tabTextSize, -1);
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

    public void setParentCoverView(ViewGroup parentCoverView) {
        mParentCoverView = parentCoverView;
    }


    public void setOnFilterCoverHolderEventListener(OnFilterCoverHolderEventListener listener) {
        mOnFilterCoverHolderEventListener = listener;
    }

    public void setOnFilterShowListener(OnFilterShowListener listener) {
        mOnFilterShowListener = listener;
    }

    /**
     * @param title     筛选标题
     * @param data      筛选数据
     * @param popupType 筛选类别
     * @return view 本身
     */
    public FilterTabLayout addTab(String title, FilterGroup data, int popupType) {
        FilterTabItemHolder tabHolder = new FilterTabItemHolder(getContext())
                .setColors(mTabTextColorNormal, mTabTextColorFocus, mTabTextColorChecked)
                .setIcons(mTabIconUpResId, mTabIconDownResId)
                .setTitle(title)
                .setMaxLines(mTabMaxLines)
                .setPosition(mTabHolderList.size())
                .setOnTabStateChangeListener(this);

        if (mTabTextSize != -1) {
            tabHolder.setSize((float) mTabTextSize);
        }

        //将筛选项布局加入view
        View tabView = tabHolder.getView();
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);
        tabView.setLayoutParams(params);
        tabView.setPadding(0, 0, 0, 0);
        addView(tabView);

        //筛选类型实体加载器
        if (mPopEntityLoader == null) {
            mPopEntityLoader = new FilterCoverLoaderImp();
        }
        SuperFilterCoverHolder filterCoverHolder = mPopEntityLoader.getPopEntity(getContext(), data, this, popupType);

        //进行缓存
        mTabHolderList.add(tabHolder);
        mFilterCoverList.add(filterCoverHolder);

        tabHolder.setPanelChecked(filterCoverHolder.getData().isChecked());
        tabHolder.resetStateUI();
        return this;
    }

    public void setTabTitle(int position, String title) {
        mTabHolderList.get(position).setTitle(title);
    }

    public void forceTabHolderClick(int position) {
        mTabHolderList.get(position).onFilterTabClick();
    }

    @Override
    public void onTabOpen(int position) {
        closeAllTabButPosition(position);
        mFilterCoverList.get(position).resetBackValue();

        if (mParentCoverView.getVisibility() == VISIBLE) {
            // todo: 如果当前Cover是显示状态，并且当前Cover显示位置不是tab的打开位置，先释放Cover再打开
            if (mParentCoverView.getTag() == null || (int) mParentCoverView.getTag() != position) {
                hideFilterCover();
                showFilterCover(position);
            }
        } else {
            showFilterCover(position);
            if (mOnFilterShowListener != null) {
                mOnFilterShowListener.onFilterShow(true);
            }
        }
    }

    @Override
    public void onTabClose(int position) {
        hideFilterCover();
        if (mOnFilterShowListener != null) {
            mOnFilterShowListener.onFilterShow(false);
        }
    }

    @Override
    public boolean onConfirm(SuperFilterCoverHolder filterCoverHolder, FilterGroup data) {
        if (mOnFilterCoverHolderEventListener != null) {
            if (mOnFilterCoverHolderEventListener.onConfirm(filterCoverHolder, data)) {
                forceConfirm(filterCoverHolder);
            } else {
                // todo: 不关闭
            }
        } else {
            forceConfirm(filterCoverHolder);
        }
        return true;
    }

    public void forceConfirm(SuperFilterCoverHolder filterCoverHolder) {
        forceTabClose(filterCoverHolder);
        hideFilterCover();
        if (mOnFilterShowListener != null) {
            mOnFilterShowListener.onFilterShow(false);
        }
    }

    public void forceConfirm(int position) {
        forceConfirm(mFilterCoverList.get(position));
    }

    @Override
    public void onReset(SuperFilterCoverHolder filterCoverHolder, FilterGroup data) {
        if (mOnFilterCoverHolderEventListener != null) {
            mOnFilterCoverHolderEventListener.onReset(filterCoverHolder, data);
        }
        forceTabClose(filterCoverHolder);
        hideFilterCover();
        if (mOnFilterShowListener != null) {
            mOnFilterShowListener.onFilterShow(false);
        }
    }

    @Override
    public void onCancel(SuperFilterCoverHolder filterCoverHolder) {
        if (mOnFilterCoverHolderEventListener != null) {
            mOnFilterCoverHolderEventListener.onCancel(filterCoverHolder);
        }
        forceTabClose(filterCoverHolder);
        filterCoverHolder.backValue();
        forceRefresh();
        hideFilterCover();
        if (mOnFilterShowListener != null) {
            mOnFilterShowListener.onFilterShow(false);
        }
    }

    private void showFilterCover(int position) {
        if (mParentCoverView == null) {
            throw new IllegalArgumentException("没有设置父遮盖");
        }

        SuperFilterCoverHolder filterCoverHolder = mFilterCoverList.get(position);
        View filterCoverView = filterCoverHolder.getView();
        filterCoverView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mParentCoverView.removeAllViews();
        mParentCoverView.addView(filterCoverView);
        mParentCoverView.setVisibility(VISIBLE);

        mParentCoverView.setTag(position);
    }

    private void hideFilterCover() {
        if (mParentCoverView == null) {
            throw new IllegalArgumentException("没有设置父遮盖");
        }

        mParentCoverView.removeAllViews();
        mParentCoverView.setVisibility(GONE);
    }

    private void forceTabClose(SuperFilterCoverHolder filterCoverHolder) {
        int position = mFilterCoverList.indexOf(filterCoverHolder);
        if (mTabHolderList.get(position).isOpen()) {
            mTabHolderList.get(position).setOpen(false);
            mTabHolderList.get(position).resetStateUI();
        }
        for (int i = 0; i < mTabHolderList.size(); i++) {
            mTabHolderList.get(i).setPanelChecked(mFilterCoverList.get(i).getData().isChecked());
            mTabHolderList.get(i).resetStateUI();
        }
    }

    private void closeAllTabButPosition(int position) {
        for (int i = 0; i < mTabHolderList.size(); i++) {
            if (i != position && mTabHolderList.get(i).isOpen()) {
                mTabHolderList.get(i).setOpen(FilterTabItemHolder.STATE_CLOSE);
                mTabHolderList.get(i).resetStateUI();

                // todo: 添加恢复上一次状态逻辑
                mFilterCoverList.get(i).backValue();
                forceRefresh(i);
            }
        }
    }

    public void forceRefresh() {
        for (SuperFilterCoverHolder holder : mFilterCoverList) {
            holder.forceRefresh();
            forceTabClose(holder);
        }
    }

    public void forceRefresh(int position) {
        mFilterCoverList.get(position).forceRefresh();
    }
}
