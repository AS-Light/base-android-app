package jx.android.staff.app;

import android.content.Intent;

import androidx.lifecycle.LifecycleService;

public class AppService extends LifecycleService {

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        initObserver();
    }

    private void initObserver() {

    }
}
