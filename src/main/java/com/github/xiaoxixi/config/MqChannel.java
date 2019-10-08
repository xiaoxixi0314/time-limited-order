package com.github.xiaoxixi.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqChannel {

    @Value("${rabbit.host}")
    private String host;

    @Value("${rabbit.user}")
    private String user;

    @Value("${rabbit.pwd}")
    private String pwd;

    @Value("${rabbit.virtual.host}")
    private String vhost;

    @Bean
    public Channel channel() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // 端口默认5672
        factory.setHost(host);
        factory.setUsername(user);
        factory.setPassword(pwd);
        factory.setVirtualHost(vhost);
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

}
