package cn.uninasa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 * 这是固定的业务流程状态，不应该让用户随意修改
 * 
 * @author Sayil
 */
@Getter
@AllArgsConstructor
public enum OrderStatus implements IDictEnum {
    
    PENDING_PAYMENT("待支付","1",""),
    PAID( "已支付","2",""),
    SHIPPED( "已发货","3",""),
    COMPLETED( "已完成","4",""),
    CANCELLED( "已取消","9","");

    private final String name;
    private final String code;
    private final String desc;

}
