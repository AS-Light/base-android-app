package jx.android.staff.api.model.items;

import java.math.BigDecimal;

import jx.android.staff.api.model.simple.Person;
import lombok.Data;

@Data
public class PersonOrderItem {
    Long id;
    String orderCode;
    BigDecimal orderPrice;
    Integer orderStatus;
    Integer orderPayMode;
    BigDecimal orderCallbackPrice;
    Long userId;
    Long storeId;
    Long storeManagerId;
    Long couponId;
    BigDecimal couponCutPrice;
    String bak;
}
