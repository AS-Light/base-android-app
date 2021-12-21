package jx.android.staff.app;

import jx.android.staff.acts.base.BaseActivity;

import java.util.Stack;


/**
 * activity堆栈式管理
 *
 * @author Howard
 * @created 2014年10月30日 下午6:22:05
 */
public class AppManager {

    private static Stack<BaseActivity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }

        if (activityStack == null) {
            activityStack = new Stack<BaseActivity>();
        }

        return instance;
    }

    public static boolean isEmpty() {
        return activityStack.isEmpty();
    }

    public static int getActivityCount() {
        return activityStack.size();
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public static BaseActivity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (BaseActivity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(BaseActivity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public BaseActivity currentActivity() {
        BaseActivity activity = activityStack.lastElement();
        return activity;
    }

    public BaseActivity getActivityAtPosition(int position) {
        return activityStack.get(position);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        BaseActivity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(BaseActivity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void removeActivity(BaseActivity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (BaseActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    public void backToActivity(Class<?> cls) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (null != activityStack.get(i) && activityStack.get(i).getClass().equals(cls)) {
                return;
            } else if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }
}
