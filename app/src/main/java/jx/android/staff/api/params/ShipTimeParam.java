package jx.android.staff.api.params;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShipTimeParam extends BaseObservable implements Serializable {
    public String shipTimeStr;
}
