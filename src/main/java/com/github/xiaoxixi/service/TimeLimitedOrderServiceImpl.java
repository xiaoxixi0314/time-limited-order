package com.github.xiaoxixi.service;

import com.alibaba.fastjson.JSON;
import com.github.xiaoxixi.Constants;
import com.github.xiaoxixi.domain.OrderMessage;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TimeLimitedOrderServiceImpl implements ITimeLimitedOrderService {

    @Autowired
    private Channel channel;



    private boolean checkOrderIsPaid(Long orderId) {
        return false;
    }

    @Override
    public boolean sendOrderToMQ(Long orderId){
        try {
            OrderMessage orderMsg = new OrderMessage();
            orderMsg.setOrderId(orderId);
            String message = JSON.toJSONString(orderMsg);

            channel.exchangeDeclare(Constants.ORDER_EXCHANGE, BuiltinExchangeType.DIRECT);

            channel.basicPublish();
        } catch (Exception e) {

        }

        return false;
    }

    @Override
    public boolean consumerTimeLimitedOrder(OrderMessage message) {
        return false;
    }
}
