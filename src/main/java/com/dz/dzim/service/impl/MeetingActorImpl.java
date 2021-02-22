package com.dz.dzim.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.common.ResultWebSocket;
import com.dz.dzim.config.SpringContextUtil;
import com.dz.dzim.pojo.vo.MsgVo;
import com.dz.dzim.service.MeetingActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class MeetingActorImpl implements MeetingActor {
    private MeetingBase meetingBase = (MeetingBase) SpringContextUtil.getApplicationContext().getBean("meetingBase");
    private final MeetingBase mMeeting;
    private final String mUserId;
    private final String userType;

    private WebSocketSession mWebSocketSession;

    public MeetingActorImpl(MeetingBase meeting, String userId,String userType) {
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
    public void sayWellcome(String type,JSONObject jsonObject) throws Exception {
        JSONObject json = new JSONObject();
        json.put("Type", type);
        json.put("STime", System.currentTimeMillis());
        json.put("Serial", meetingBase.nextSerial());

        JSONObject content = new JSONObject();

        json.put("content", content);
        json.put("msg", "连接创建成功--->"+jsonObject.toString());
        TextMessage textMessage = new TextMessage(JSONObject.toJSONString(json));
        this.mWebSocketSession.sendMessage(textMessage);
    }

    @Override
    public void sendMsg(MsgVo msgVo) throws Exception {
        msgVo.setSerial(meetingBase.nextSerial());
        TextMessage textMessage = new TextMessage(JSONObject.toJSONString(msgVo));
        this.getWebscoket().sendMessage(textMessage);

    }
}
