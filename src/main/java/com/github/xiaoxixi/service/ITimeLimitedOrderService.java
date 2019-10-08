package com.github.xiaoxixi.service;

import com.github.xiaoxixi.domain.OrderMessage;

public interface ITimeLimitedOrderService {


    /**
     * 将订单放入消息队列中
     * @param orderId
     * @return
     */
    boolean sendOrderToMQ(Long orderId);

    /**
     * 消费限时订单消息
     * 如该订单已支付，不做任何处理
     * 如该订单未支付，则将订单状态修改成CLOSED
     * @param message
     * @return
     */
    boolean consumerTimeLimitedOrder(OrderMessage message);

}
