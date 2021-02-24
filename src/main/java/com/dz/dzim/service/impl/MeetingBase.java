package com.dz.dzim.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.service.Meeting;
import com.dz.dzim.service.MeetingActor;
import com.dz.dzim.service.MeetingControl;
import com.dz.dzim.service.impl.MeetingActorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

/**
 * 会场的基础类
 *
 * @author qianyangdong
 */
@Service
public abstract class MeetingBase implements Meeting {
    private final String mId = UUID.randomUUID().toString(); //会场唯一编号

    private final Map<String, MeetingActorImpl> mActors = new HashMap();
    private final Map<String, MeetingActorImpl> kfActors = new HashMap();

    @Autowired
    private MeetingControl meetingControl;

    @Override
    public final String getId() {
        return mId;
    }

    @Override
    public MeetingActorImpl createActor(String userId, String userType) throws Exception {
        MeetingActorImpl actor;
        synchronized (this.mActors) {
            actor = this.mActors.get(userId);
            if (null == actor) {
                actor = new MeetingActorImpl(this, userId, userType);
                this.mActors.put(userId, actor);
            }
        }
        return actor;
    }

    @Override
    public void closedActor(String userId, String userType) {
        MeetingActorImpl actor;
        synchronized (this.mActors) {
            actor = this.mActors.get(userId);
            if (actor != null) {
                this.mActors.remove(userId);
            }
        }
        //return actor;
    }

    @Override
    public MeetingActor getActor(String actorId) throws Exception {
        MeetingActor actor;
        synchronized (this.mActors) {
            actor = this.mActors.get(actorId);
        }
        return actor;
    }

    @Override
    public List<MeetingActor> getActors() throws Exception {
        List list = new ArrayList();
        synchronized (this.mActors) {
            list.addAll(this.mActors.values());
        }
        return list;
    }

    @Override
    public  Map<String,Object> getActorsByMainWaiter() throws Exception {
        List list = new ArrayList();
        List listSession = new ArrayList();
        Map<String,Object> map =new HashMap<>();
        synchronized (this.mActors) {

            Map<String, MeetingActorImpl> mActors = this.mActors;
            Set<String> strings = mActors.keySet();
            for (String s : strings) {
                MeetingActorImpl meetingActor = mActors.get(s);
                if (mActors.get(s).getUserType().equals("member")) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", meetingActor.getId());
                    jsonObject.put("userType", meetingActor.getUserType());
                    list.add(jsonObject);

                } else {
                    WebSocketSession webscoket = meetingActor.getWebscoket();
                    listSession.add(webscoket);
                }

            }
            map.put("memberList",list);
            map.put("waiterSessionList",listSession);

        }
        return map;
    }

    @Override
    public void waitingList(String type, Map<String, Object> map)  throws Exception{
        List memberList = (List) map.get("memberList");
        List<WebSocketSession> waiterSessionList = (List<WebSocketSession>) map.get("waiterSessionList");
        if (waiterSessionList.size() > 0 && null != waiterSessionList) {
            JSONObject json = new JSONObject();
            json.put("type", type);
            json.put("STime", System.currentTimeMillis());
            json.put("Serial", this.nextSerial());
            json.put("content", JSONObject.toJSONString(memberList));
            TextMessage textMessage = new TextMessage(JSONObject.toJSONString(json));
            for (WebSocketSession session : waiterSessionList){
                session.sendMessage(textMessage);
            }
        }
    }

    private long mSerialSeed = 0; //消息流水号的种子

    /**
     * 取下一个可用的消息流水号
     * @return 消息流水号
     */
    public final long nextSerial()
    {
        synchronized(this)
        {
            return ++ this.mSerialSeed;
        }
    }

}
