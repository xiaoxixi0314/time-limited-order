package com.github.xiaoxixi.controller;

import com.github.xiaoxixi.domain.OrderVO;
import com.github.xiaoxixi.enums.OrderStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * 下单
     * @return
     */
    @PostMapping("/init")
    public OrderVO init() {
        OrderVO order = new OrderVO();
        order.setOrderId(System.currentTimeMillis());
        order.setStatus(OrderStatus.INIT);
        // todo send message to rabbitmq
        return order;
    }


}
