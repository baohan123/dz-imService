package com.dz.dzim.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.pojo.vo.MsgVo;
import com.dz.dzim.service.MeetingActor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class MeetingActorImpl implements MeetingActor {


    private final MeetingBase mMeeting;
    private final String mUserId;
    private final String userType;

    private WebSocketSession mWebSocketSession;

    private String meettingType;

    public MeetingActorImpl(MeetingBase meeting, String userId, String userType) {
        this.mMeeting = meeting;
        this.mUserId = userId;
        this.userType = userType;
    }

    @Override
    public String getId() {
        return this.mUserId;
    }

    @Override
    public String getUserType() throws Exception {
        return this.userType;
    }

    @Override
    public final MeetingBase getMeeting() {
        return this.mMeeting;
    }

    @Override
    public void setWebsocket(WebSocketSession session) throws Exception {
        this.mWebSocketSession = session;
    }

    @Override
    public WebSocketSession getWebscoket() throws Exception {
        return this.mWebSocketSession;
    }

    @Override
    public void setMeettingType(String meettingType) {
        this.meettingType = meettingType;
    }

    @Override
    public void sayWellcome(String type, String meetingId) throws Exception {

        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("sTime", System.currentTimeMillis());

        json.put("serial", mMeeting.nextSerial());
        json.put("content", "连接创建成功" + meetingId);
        TextMessage textMessage = new TextMessage(JSONObject.toJSONString(json));
        this.mWebSocketSession.sendMessage(textMessage);
    }

    @Override
    public void sendMsg(MsgVo msgVo) throws Exception {
        msgVo.setSerial(mMeeting.nextSerial());
        TextMessage textMessage = new TextMessage(JSONObject.toJSONString(msgVo));
        this.getWebscoket().sendMessage(textMessage);

    }


}
