package com.github.xiaoxixi.consumer;

import com.alibaba.fastjson.JSON;
import com.github.xiaoxixi.Constants;
import com.github.xiaoxixi.domain.OrderMessage;
import com.github.xiaoxixi.service.OrderManager;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TimeLimitedOrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeLimitedOrderConsumer.class);

    @Autowired
    private Channel channel;

    @Autowired
    private OrderManager orderManager;

    @PostConstruct
    public void consumer(){
        try {
            // 普通交换器和队列的声明
            channel.exchangeDeclare(Constants.ORDER_EXCHANGE, BuiltinExchangeType.TOPIC);

            Map<String, Object> args = new HashMap<>();
            args.put(Constants.ChannelConstant.ARGS_MESSAGE_TTL, 20000);
            args.put(Constants.ChannelConstant.ARGS_MESSAGE_DLX, Constants.DLX_ORDER_EXCHANGE);
            args.put(Constants.ChannelConstant.ARGS_MESSAGE_ELX_ROUTE_KEY, Constants.ChannelConstant.DLX_ROUTE_KEY);
            channel.queueDeclare(Constants.ChannelConstant.QUEUE_NAME,
                    false,
                    false,
                    false,
                    args);
            channel.queueBind(Constants.ChannelConstant.QUEUE_NAME,
                    Constants.ORDER_EXCHANGE,
                    Constants.ChannelConstant.ROUTE_KEY);


            // 死信交换器和队列的声明
            channel.exchangeDeclare(Constants.DLX_ORDER_EXCHANGE, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(Constants.ChannelConstant.DLX_QUEUE_NAME,
                    false,
                    false,
                    false,
                    null);
            channel.queueBind(Constants.ChannelConstant.DLX_QUEUE_NAME,
                    Constants.DLX_ORDER_EXCHANGE,
                    "#");


            LOGGER.info("waiting for message......");
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String message = new String(body, Constants.CHARSET_UTF_8);
                    String exchange = envelope.getExchange();
                    String routeKey = envelope.getRoutingKey();
                    LOGGER.info("received message:{} from {}:{}", message, exchange, routeKey);
                    OrderMessage orderMessage = JSON.parseObject(message, OrderMessage.class);
                    if (Objects.isNull(orderMessage)) {
                        LOGGER.error("illegal message, will not consume");
                        return;
                    }
                    Long id = orderMessage.getOrderId();
                    if (!orderManager.checkOrderPaid(id)) {
                        LOGGER.info("order is not paid, will close. Order id is :{}", id);
                        orderManager.closeOrder(id);
                    }
                }
            };
            channel.basicConsume(Constants.ChannelConstant.DLX_QUEUE_NAME,
                    true,
                    consumer);

        } catch (Exception e) {
            LOGGER.error("consumer message error:", e);
        }
    }

    @PreDestroy
    public void channelClose() throws Exception{
        if (Objects.isNull(channel)) {
            channel.close();
        }
    }
}
