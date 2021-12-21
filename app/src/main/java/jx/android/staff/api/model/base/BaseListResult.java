package jx.android.staff.api.model.base;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseListResult<T> extends BaseResult {
    private ArrayList<T> list;
}
