# 限时订单
## rabbitmq实现限时订单
### 实现原理：
* 下单时产生订单并生产消息
* 消息进入队列，并设置消息的过期时间
* 消息绑定死信队列，消息过期仍然没有消费，消息会进入死信队列
* 消息消费者消费死信队列中的消息，检查订单是否为已支付，否则关闭订单
