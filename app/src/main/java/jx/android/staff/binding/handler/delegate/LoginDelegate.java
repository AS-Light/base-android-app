package jx.android.staff.binding.handler.delegate;

import jx.android.staff.api.params.LoginParam;
import jx.android.staff.binding.handler.delegate.base.Delegate;

public interface LoginDelegate extends Delegate {
    void login(LoginParam loginParam);
}
