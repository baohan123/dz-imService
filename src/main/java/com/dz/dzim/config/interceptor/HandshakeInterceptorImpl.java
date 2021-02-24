package com.dz.dzim.config.interceptor;

import com.dz.dzim.common.SysConstant;
import com.dz.dzim.mapper.MeetingActorDao;
import com.dz.dzim.mapper.MeetingDao;
import com.dz.dzim.pojo.doman.MeetingActorEntity;
import com.dz.dzim.service.Meeting;
import com.dz.dzim.service.MeetingActor;
import com.dz.dzim.service.MeetingControl;
import com.dz.dzim.service.impl.SmallMeetingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Date;
import java.util.Map;

/**
 * @author baohan
 * @className 拦截器
 * @description TODO
 * @date 2021/1/28 14:33
 */
@Component
public class HandshakeInterceptorImpl implements org.springframework.web.socket.server.HandshakeInterceptor {
    @Autowired
    private MeetingControl meetingControl;
    @Autowired
    private MeetingActorDao meetingActorDao;


    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
//        HttpHeaders headers = serverHttpRequest.getHeaders();
        Map<String, Object> stringStringMap = queryToMap(serverHttpRequest.getURI().getQuery(),map);
            //   String sessionid = headers.getFirst("sessionid");
//        System.out.println("拦截器获取的sessionid:" + sessionid);
       // String userId = request.getService().getSession().getId();
        String userId = (String) stringStringMap.get("talkerId");
        String meetingId = (String) stringStringMap.get("meetingId");
        String userType = (String) stringStringMap.get("talkerType");
        //String meetingId = request.getParameter("MeetingId").getString();
        Meeting meeting = meetingControl.getMeetingById(meetingId);
        meeting.createActor(userId,userType);
        if(meeting.getType().equals(SmallMeetingImpl.SMALL_MEETING)){
            MeetingActorEntity meetingActorEntity = new MeetingActorEntity(null, new Long(userId), userType,
                    null, meetingId, null,
                    new Date(), null,
                    SysConstant.ZERO, null);
            meetingActorDao.insert(meetingActorEntity);
        }

//        attributes.put("meetingId", meetingId);
//        attributes.put("actorId", actor.getId());
//        if (sessionid != null && sessionid != "") {
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
