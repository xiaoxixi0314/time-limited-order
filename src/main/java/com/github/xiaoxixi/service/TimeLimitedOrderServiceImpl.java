package com.github.xiaoxixi.service;

import com.alibaba.fastjson.JSON;
import com.github.xiaoxixi.Constants;
import com.github.xiaoxixi.domain.OrderMessage;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class TimeLimitedOrderServiceImpl implements TimeLimitedOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeLimitedOrderServiceImpl.class);

    @Autowired
    private Channel channel;

    @Autowired
    private OrderManager orderManager;

    @Override
    public boolean sendOrderToMQ(Long orderId){
        try {
            OrderMessage orderMsg = new OrderMessage();
            orderMsg.setOrderId(orderId);
            String message = JSON.toJSONString(orderMsg);

            channel.exchangeDeclare(Constants.ORDER_EXCHANGE,
                    BuiltinExchangeType.TOPIC);
            LOGGER.info("will send message to mq, order id:{}...", orderId);
            channel.basicPublish(Constants.ORDER_EXCHANGE,
                    Constants.ChannelConstant.ROUTE_KEY,
                    null,
                    message.getBytes());
            LOGGER.info("sended message to mq...", orderId);
        } catch (Exception e) {
            LOGGER.error("send message to mq error:", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean consumerTimeLimitedOrder(OrderMessage message) {
        if (Objects.isNull(message)) {
            LOGGER.error("message cant be null");
            return false;
        }
        Long id = message.getOrderId();
        if (!orderManager.checkOrderPaid(id)) {
            return orderManager.closeOrder(id);
        }
        return true;
    }
}
