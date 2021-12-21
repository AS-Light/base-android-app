package jx.android.staff.api.model;

import java.util.ArrayList;

import jx.android.staff.api.model.base.BaseResult;
import jx.android.staff.api.model.items.PersonOrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonOrderList extends BaseResult {
    private ArrayList<PersonOrderItem> orderList;
}
