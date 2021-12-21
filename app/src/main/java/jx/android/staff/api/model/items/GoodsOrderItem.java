package jx.android.staff.api.model.items;

import java.util.List;

import jx.android.staff.api.model.simple.Goods;
import lombok.Data;

@Data
public class GoodsOrderItem {
    Goods goods;    // 商品
    Integer count;  // 数量

    List<Integer> childrenOrders;   // 分拆成小单，每单数量
}
