package jx.android.staff.api.callback;

import android.widget.Toast;

import jx.android.staff.app.AppContext;

public abstract class SimpleSuccessHttpCallbackListener implements OnHttpCallbackListener {
    @Override
    public void onFault(String errorMsg) {
        Toast.makeText(AppContext.getInstance(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
