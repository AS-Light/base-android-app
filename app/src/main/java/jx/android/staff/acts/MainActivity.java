package jx.android.staff.acts;

import androidx.navigation.ui.AppBarConfiguration;

import jx.android.staff.R;
import jx.android.staff.acts.base.BaseActivity;
import jx.android.staff.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    AppBarConfiguration mAppBarConfiguration;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initBinding() {
    }

    @Override
    public void initViews() {
    }

    @Override
    public void initObserver() {
    }

    @Override
    public void initData() {
        // 两次Back退出app
        couldExitApp = true;
    }

    @Override
    public String getActivityName() {
        return "主页";
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
