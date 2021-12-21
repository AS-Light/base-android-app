package jx.android.staff.api.model.simple;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Shop {
    Long id;
    String shopName;
    String managerName;
    String managerAvatar;
    String phone;
    String address;
    BigDecimal lat;
    BigDecimal lng;
}
