package com.github.xiaoxixi.domain;

import com.github.xiaoxixi.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderVO {

    private Long orderId;

    private OrderStatus status;
}
