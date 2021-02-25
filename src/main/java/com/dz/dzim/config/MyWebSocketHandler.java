package com.dz.dzim.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.common.ResultWebSocket;
import com.dz.dzim.common.inter.CodeEnum;
import com.dz.dzim.controller.ChatController;
import com.dz.dzim.mapper.MeetingChattingDao;
import com.dz.dzim.pojo.doman.MeetingChattingEntity;
import com.dz.dzim.pojo.vo.MsgVo;
import com.dz.dzim.pojo.vo.ResponseVO;
import com.dz.dzim.service.*;
import com.dz.dzim.service.impl.MainMeetingImpl;
import com.dz.dzim.service.impl.MeetingActorImpl;
import com.dz.dzim.service.impl.MeetingBase;
import com.dz.dzim.service.impl.MeetingControlImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private MeetingChattingDao meetingChattingDao;

    @Autowired
    private MeetingControlImpl meetingControlImpl;

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        System.out.println("ping...");
    }

    /**
     * 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String actorId = String.valueOf(attributes.get("talkerId")); //会场参与者编号
        String meetingId = String.valueOf(attributes.get("meetingId"));
        String actorType = String.valueOf(attributes.get("talkerType"));
        Meeting meeting = meetingControl.getMeetingById(meetingId);
        String meettingType = meeting.getType();
        //根据参与者编号，检索对应的参与者
        MeetingActor actor = meeting.getActor(actorId);
        if (null == actor) {
            log.error("连接异常");
        }
        //在参与者和SOCKET之间建立关联
        actor.setWebsocket(session);
        actor.setMeettingType(meettingType);
        actor.sayWellcome(meeting.getType(), meetingId);
        //待抢列表
        if (meettingType.equals(MainMeeting.MAIN_MEETING)) {
            Map<String, Object> map = meeting.getActorsByMainWaiter();

            meeting.waitingList("0x60", map);
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
        String actorType = String.valueOf(attributes.get("talkerType"));
        String userId = String.valueOf(attributes.get("talkerId"));
        Meeting meeting = meetingControl.getMeetingById(meetingId);
        long currentTimeMillis = System.currentTimeMillis();
        MsgVo msgVo = new MsgVo(type, currentTimeMillis, 0, userId, actorType, userId, null, msg);
        List<MeetingActor> actors = meeting.getActors();
        switch (type) {
            case "0x25":
                meeting.pingSoket("0x25", session);
                return;
            default:
                break;
        }
        type = getType(type);

        for (MeetingActor actor : actors) {
            actor.sendMsg(msgVo);
        }
        MeetingChattingEntity meetingChattingEntity = new MeetingChattingEntity(
                null, meetingId, new Long(userId), actorType, null,
                type, currentTimeMillis,
                null, msg, new Long(0), null, null
        );
        meetingChattingDao.insert(meetingChattingEntity);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // TODO Auto-generated method stub
        log.info("connect websocket closed.......");

        Map<String, Object> attributes = session.getAttributes();
        String meetingId = String.valueOf(attributes.get("meetingId"));
        String userId = String.valueOf(attributes.get("talkerId"));
        String actorType = String.valueOf(attributes.get("talkerType")); //会场参与者编号
        Meeting meeting = meetingControl.getMeeting(meetingId);
        meeting.closedActor(userId, actorType, meetingId);

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

    public String getType(String type) {
        switch (type) {
            case "0x40":
                return "0x41";
            case "0x42":
                return "0x43";
            case "0x44":
                return "0x45";
            case "0x26":
                return "0x26";
            default:
                return "";
        }
    }

}
