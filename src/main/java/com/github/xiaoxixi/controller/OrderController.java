package com.github.xiaoxixi.controller;

import com.github.xiaoxixi.service.OrderManager;
import com.github.xiaoxixi.service.TimeLimitedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderManager orderManager;

    @Autowired
    private TimeLimitedOrderService timeLimitedOrderService;

    /**
     * 下单
     * @return
     */
    @GetMapping("/init")
    public boolean init() {
        // 生成一条订单数据，并将订单号发送到mq中
        Long orderId = orderManager.initOrder();
        return timeLimitedOrderService.sendOrderToMQ(orderId);
    }


}
