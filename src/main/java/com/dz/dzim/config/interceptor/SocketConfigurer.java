package com.dz.dzim.config.interceptor;

import com.dz.dzim.config.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author baohan
 * @className WebSocketConfigurerConfig
 * @description 配置类WebSocketConfig
 * @date 2021/1/28 14:05
 */
@Configuration
@EnableWebSocket
public class SocketConfigurer implements WebSocketConfigurer {
    @Autowired
    private MyWebSocketHandler httpAuthHandler;

    @Autowired
    private HandshakeInterceptorImpl handshakeInterceptor;

    /**
     * 暴露出的 ws 路径
     */
    //private String websocktUrl = "connectWebSocket2/{userId}";
    private String websocktUrl = "connectWebSocket";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(httpAuthHandler, websocktUrl)
                .addInterceptors(handshakeInterceptor)
                .setAllowedOrigins("*");//setAllowedOrigins这个是关闭跨域校验
    }
}
