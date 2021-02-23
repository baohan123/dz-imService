package com.dz.dzim.config;

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

    private static final String MAIN_MEETING = "mainMeeting";
    private static final String SMALL_MEETING = "smallMeeting";

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MeetingControl meetingControl;

    @Autowired
    private MeetingChattingDao meetingChattingDao;

    @Autowired
    private MeetingControlImpl meetingControlImpl;

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
        String actorType = String.valueOf(attributes.get("talkerType")); //会场参与者编号

        String meetingId = String.valueOf(attributes.get("meetingId"));
        Meeting meeting = null;
        Map<String, Meeting> meetingMap = meetingControl.getMeetingById(meetingId);
        Meeting mainMeeting = meetingMap.get(MAIN_MEETING);
        String meettingType = null;
        if (null == mainMeeting) {
            //小会场
            meeting = meetingMap.get(SMALL_MEETING);
            meettingType = SMALL_MEETING;
        } else {
            //主会场
            meeting = mainMeeting;
            meettingType = MAIN_MEETING;
        }
        //根据参与者编号，检索对应的参与者
        MeetingActor actor = meeting.getActor(actorId);
        if (null == actor) {
            actor = meeting.createActor(actorId, actorType);
        }
        //在参与者和SOCKET之间建立关联
        actor.setWebsocket(session);
        actor.setMeettingType(meettingType);
        actor.sayWellcome("0x20", meetingId); //发送欢迎辞

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
        String userId = String.valueOf(attributes.get("actorId"));
        Map<String, Meeting> meetingById = meetingControl.getMeetingById(meetingId);
        Meeting smallMeeting = meetingById.get(SMALL_MEETING);
        type = getType(type);
        long currentTimeMillis = System.currentTimeMillis();
        //如果没有小会场
        if (null == smallMeeting) {
           // meetingControl.createSmallMeeting(String userId)
            addSmallMeetings(userId);
            return;
        }
        MsgVo msgVo = new MsgVo(type, currentTimeMillis, 0, userId, actorType, userId, null, msg);
        Meeting meetingSmall = meetingControlImpl.getMeetingByIdSmall(meetingId);


        Map<String, MeetingActorImpl> actorAll = meetingSmall.getActorAll();
        if(actorAll.size() <1){
            log.error("当前会场只要一个人，会场未建立成功,重新发送");
            meetingControl.removeSmallMeeting(meetingId);
            return;
        }
        String addrId=null;
        String addrType=null;
        Set<String> strings = actorAll.keySet();
        for (String s : strings) {
            MeetingActorImpl meetingActor = actorAll.get(s);
            String userIds = meetingActor.getId();
            if(!userId.equals(userIds)){
                addrId = userIds;
                addrType = meetingActor.getUserType();
            }
            meetingActor.sendMsg(msgVo);
        }
        MeetingChattingEntity meetingChattingEntity = new MeetingChattingEntity(
                null,meetingId, new Long(userId), actorType, null,
                type, currentTimeMillis,
                null, msg, new Long(addrId), addrType, null
        );
        meetingChattingDao.insert(meetingChattingEntity);
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
        String actorType = String.valueOf(attributes.get("talkerType")); //会场参与者编号
        Meeting meeting = meetingControl.getMeeting(meetingId);
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

    public void addSmallMeetings(String userId) throws Exception {
        SmallMeeting smallMeetings = meetingControl.createSmallMeeting();
        String smallMeetingsId = smallMeetings.getId();
        //先随机查询一个客服创建小会场
        MainMeeting mainMeeting = meetingControl.getMainMeeting();
        Map<String, MeetingActorImpl> actorAll = mainMeeting.getActorAll();
        for (Map.Entry<String, MeetingActorImpl> entry : actorAll.entrySet()) {
            MeetingActorImpl actor = entry.getValue();
            if ("waiter".equals(entry.getValue().getUserType())) {
                String userIds = actor.getId();
                String kfMettingId = actor.getMeeting().getId();
                WebSocketSession webscoket = actor.getWebscoket();
                mainMeeting.inviteActorToSmallMeeting(userIds, smallMeetingsId, webscoket);
                break;
            }
        }
        WebSocketSession webscoket = actorAll.get(userId).getWebscoket();
        mainMeeting.inviteActorToSmallMeeting(userId, smallMeetingsId, webscoket);
    }

}
