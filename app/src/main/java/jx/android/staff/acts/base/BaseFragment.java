package jx.android.staff.acts.base;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import jx.android.staff.app.AppLog;
import jx.android.staff.dialog.WaitingDialog;
import jx.android.staff.utils.DateUtils;

/**
 * 基础Fragment，基于Fragment、DataBinding、lifecycle
 */
public abstract class BaseFragment<SV extends ViewDataBinding> extends Fragment {

    protected SV mViewBinding;
    public View mRootView;

    boolean isCreated = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        long startTime = DateUtils.getNowTimeZone();

        // 子类赋予布局
        mRootView = bindView(inflater, container);

        // 初始化View，主要是ListView和Adapter相关内容
        initViews();
        // 初始化DataBinding相关的ViewModel、Handler、Delegate内容
        initBinding();
        // 初始化数据和网络接口调用
        initData();
        // 初始化网络接口调用后返回数据的观察者，及其处理方式
        initObserver();

        isCreated = true;
        mRootView.setOnClickListener(view -> AppLog.e("on root view click"));

        long endTime = DateUtils.getNowTimeZone();
        AppLog.e(getFragmentName() + " on create with time : " + (endTime - startTime));
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public View bindView(LayoutInflater inflater, ViewGroup container) {
        mViewBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return mViewBinding.getRoot();
    }

    /**
     * 注入Activity布局文件
     * 返回当前页面布局文件ID
     */
    public abstract int getLayoutRes();

    /**
     * 初始化View，主要是ListView和Adapter相关内容
     */
    public abstract void initViews();

    /**
     * 初始化DataBinding相关的ViewModel、Handler、Delegate内容
     */
    public abstract void initBinding();

    /**
     * 初始化数据和网络接口调用
     */
    public abstract void initData();

    /**
     * 初始化网络接口调用后返回数据的观察者，及其处理方式
     */
    public abstract void initObserver();

    public abstract String getFragmentName();

    @Override
    public void onDestroy() {
        mViewBinding.unbind();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isCreated) {
            if (isVisibleToUser) {
                refreshWithResume();
            } else {
            }
        }
    }

    public void refreshWithResume() {

    }

    private static final long WAIT_DIALOG_COUNT_DOWN = 5;
    private int waitDialogCount = 0;
    private CountDownTimer waitDialogCountDownTimer;

    /**
     * 显示Loading对话框
     * Loading对话框在最后一次打开（WAIT_DIALOG_COUNT_DOWN秒）后，关闭对话框
     * 注：Fragment显示的Loading对话框和其所属的FragmentActivity是同一个
     */
    public void showWaitDialog() {
        try {
            WaitingDialog.showLoading(getActivity());
            waitDialogCount++;

            // 关闭倒计时，开启新的倒计时
            if (waitDialogCountDownTimer != null) {
                waitDialogCountDownTimer.cancel();
            }
            waitDialogCountDownTimer = new CountDownTimer(WAIT_DIALOG_COUNT_DOWN * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    // 没事
                }

                @Override
                public void onFinish() {
                    // 倒计时后强制关闭Loading对话框
                    waitDialogCount = 1;
                    hideWaitDialog();
                }
            };
            waitDialogCountDownTimer.start();
        } catch (Exception e) {
            // 没事
        }
    }

    /**
     * 隐藏Loading对话框
     */
    public void hideWaitDialog() {
        try {
            waitDialogCount = waitDialogCount > 0 ? waitDialogCount - 1 : waitDialogCount;
            if (waitDialogCount == 0) {
                WaitingDialog.stopLoading();
            }
        } catch (Exception e) {
            // 没事
        }
    }

    public View getRtView()

    {
        return mRootView;
    }
}
