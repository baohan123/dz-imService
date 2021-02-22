package com.dz.dzim.config.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import java.util.Map;

/**
 * @author baohan
 * @className 拦截器
 * @description TODO
 * @date 2021/1/28 14:33
 */
@Component
public class HandshakeInterceptorImpl implements org.springframework.web.socket.server.HandshakeInterceptor {


    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        HttpHeaders headers = serverHttpRequest.getHeaders();
        Map<String, Object> stringStringMap = queryToMap(serverHttpRequest.getURI().getQuery(),map);
        String sessionid = headers.getFirst("sessionid");
        System.out.println("拦截器获取的sessionid:" + sessionid);
        if (sessionid != null && sessionid != "") {
//            //检查是否存在
//            if(redisUtil.isExist(sessionid)) {
//                System.out.println("通过不拦截");
//                serverHttpResponse.setStatusCode(HttpStatus.OK);
//                return true;
//            }
//            //session过期拦截
//            System.out.println("拦截请求1：sessionid已过期！");
//            serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
//            return false;
//        }
//        System.out.println("拦截请求2：sessionid为空");
//        serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
//
//
//        return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("即将进入会话...");
    }


    public Map<String, Object> queryToMap(String query,Map<String, Object> map){
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                map.put(pair[0], pair[1]);
            }else{
                map.put(pair[0], "");
            }
        }
        return map;
    }
}
