package com.dz.dzim.config;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.common.ResultWebSocket;
import com.dz.dzim.controller.ChatController;
import com.dz.dzim.pojo.vo.MsgVo;
import com.dz.dzim.service.Meeting;
import com.dz.dzim.service.MeetingActor;
import com.dz.dzim.service.MeetingControl;
import com.dz.dzim.service.impl.MeetingActorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * @author baohan
 * @className websocket 处理器
 * @description TODO
 * @date 2021/1/28 14:13
 */
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MeetingControl meetingControl;


    /**
     * 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String actorId = String.valueOf(attributes.get("actorId")); //会场参与者编号
        String actorType = String.valueOf(attributes.get("actorType")); //会场参与者编号

        String meetingId = String.valueOf(attributes.get("meetingId"));
        Meeting meeting = meetingControl.getMeetingById(meetingId);

        MeetingActor actor = meeting.getActor(actorId); //根据参与者编号，检索对应的参与者
        if (null == actor) {
            actor = meeting.createActor(actorId, actorType);
        }
        actor.setWebsocket(session); //在参与者和SOCKET之间建立关联

        actor.sayWellcome("0x20", JSONObject.parseObject(meetingId)); //发送欢迎辞
    }

    public String getType(String type) {
        switch (type) {
            case "0x40":
                return "0x41";
            case "0x42":
                return "0x43";
            case "0x44":
                return "0x45";
            default:
                return "";
        }
    }

    /**
     * 接受消息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JSONObject jsonObject = JSONObject.parseObject(payload);
        String type = jsonObject.getString("type");

        String msg = jsonObject.getString("content");
        Map<String, Object> attributes = session.getAttributes();
        String meetingId = String.valueOf(attributes.get("meetingId"));
        String actorType = String.valueOf(attributes.get("actorType")); //会场参与者编号
        String userId = String.valueOf(attributes.get("actorId"));
        MsgVo msgVo = new MsgVo(getType(type), System.currentTimeMillis(), 0, userId, actorType, userId, null, msg);
        Meeting meeting = meetingControl.getMeetingById(meetingId);
        Map<String, MeetingActorImpl> actorAll = meeting.getActorAll();
        Set<String> strings = actorAll.keySet();
        for (String s : strings) {
            MeetingActorImpl meetingActor = actorAll.get(s);
            meetingActor.sendMsg(msgVo);
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Map<String, Object> attributes = session.getAttributes();


        //  log.error("session==>userid:" + meetingId);
        //sessionManage.remove(null);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // TODO Auto-generated method stub
        log.info("connect websocket closed.......");
        if (session.isOpen()) {
            session.close();
        }
        Map<String, Object> attributes = session.getAttributes();
        String meetingId = String.valueOf(attributes.get("meetingId"));
        String userId = String.valueOf(attributes.get("actorId"));
        String actorType = String.valueOf(attributes.get("actorType")); //会场参与者编号

        Meeting meeting = meetingControl.getMeetingById(meetingId);
        meeting.closedActor(userId, actorType);


    }

    // 处理二进制消息
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        super.handleBinaryMessage(session, message);
    }

    protected void handlePingMessage(WebSocketSession session) throws Exception {
        byte[] array = new byte[1];
        array[0] = 1;
        ByteBuffer buffer = ByteBuffer.wrap(array);
        PingMessage pingMessage = new PingMessage(buffer);
        session.sendMessage(pingMessage);
    }

}
