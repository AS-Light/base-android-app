package jx.android.staff.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/***
 * PopupWindow基类
 *
 * @author pei
 * @version 1.0
 * @cretae 2016-7-21
 * @注：若要popwindow点击外部消失，则设置 this.setFocusable(true)
 *     若要popwindow点击外部不消失，不做setFocusable设置，也不要设置成this.setFocusable(false)
 *
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected View mLayoutView;
    protected int mLayoutId;
    protected Context mContext;

    public BasePopupWindow(int layoutId, Context context) {
        this.mLayoutId = layoutId;
        this.mContext = context;
        mLayoutView = LayoutInflater.from(context).inflate(mLayoutId, null);
        setWindow();
    }

    /**
     * PopupWindow基本设置
     **/
    protected void setWindow() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(mLayoutView);
        setFocusable(false);// 可点击
        // 实例化一个ColorDrawable颜色为半透明(半透明遮罩颜色代码#66000000)
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        setBackgroundDrawable(dw);
    }

    /**
     * PopupWindow背景设置
     **/
    protected void setBackground(int color) {
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(color);
        setBackgroundDrawable(dw);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    /**
     * 显示在控件正上方
     *
     * @param view     依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationTop(View view, float marginDp) {
        mLayoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = mLayoutView.getMeasuredWidth();
        int popupHeight = mLayoutView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight - dp2px(marginDp));
    }

    /**
     * 显示在控件正下方
     *
     * @param view     依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationGravityBottom(View view, float marginDp) {
        mLayoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = mLayoutView.getMeasuredWidth();
        int popupHeight = mLayoutView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2,
                location[1] + view.getHeight() + dp2px(marginDp));
    }

    /**
     * 显示在控件下方
     *
     * @param view     依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationBottom(View view, float marginDp) {
        showAsDropDown(view, 0, dp2px(marginDp));
    }


    /**
     * 显示在控件左方
     *
     * @param view     依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationLeft(View view, float marginDp) {
        mLayoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = mLayoutView.getMeasuredWidth();
        int popupHeight = mLayoutView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] - popupWidth - dp2px(marginDp), (location[1] + view.getHeight() / 2) - popupHeight / 2);
    }

    /**
     * 显示在控件右方
     *
     * @param view     依赖的控件
     * @param marginDp 设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationRight(View view, float marginDp) {
        mLayoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = mLayoutView.getMeasuredWidth();
        int popupHeight = mLayoutView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() + dp2px(marginDp), (location[1] + view.getHeight() / 2) - popupHeight / 2);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAtLocation(View anchor, int gravity, int x, int y) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAtLocation(anchor, gravity, x, y);
    }

    /**
     * dp转px
     **/
    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, mContext.getResources().getDisplayMetrics());
    }
}