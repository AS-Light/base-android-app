package jx.android.staff.api.model.items;

import jx.android.staff.api.model.simple.Shop;
import lombok.Data;

@Data
public class DistributionShopItem {
    Long id;
    String receiver;
    String address;
    String phone;
    Integer status;
}
