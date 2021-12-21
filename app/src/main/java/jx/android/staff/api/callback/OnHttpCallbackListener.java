package jx.android.staff.api.callback;

public interface OnHttpCallbackListener {
    void onSuccess(String result);

    void onFault(String errorMsg);
}
