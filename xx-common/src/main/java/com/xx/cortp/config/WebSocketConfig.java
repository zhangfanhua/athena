package com.xx.cortp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Auther: Frank.Zhang
 * @Date: 2020/4/20 15:47
 * @Description:
 * https://blog.csdn.net/qq_35638499/article/details/80928356
 * springBoot2.0版本后使用 实现WebSocketMessageBrokerConfigurer接口；
 * 2.0以下版本继承AbstractWebSocketMessageBrokerConfigurer
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册一个Stomp 协议的endpoint指定URL为myWebSocket,并用.withSockJS()指定 SockJS协议。.setAllowedOrigins("*")设置跨域
        registry.addEndpoint("/webSocket").setAllowedOrigins("*").withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //配置消息代理(message broker)
        //将消息传回给以‘/topic’开头的客户端
        config.enableSimpleBroker("/topic");
    }

}
