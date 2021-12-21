package jx.android.staff.api.params;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginParam extends BaseObservable implements Serializable {
    @Bindable
    public String password;
    @Bindable
    public String verifyCode;
    @Bindable
    public String username;
}
