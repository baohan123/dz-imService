package com.dz.dzim.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dz.dzim.common.ResultWebSocket;
import com.dz.dzim.common.SysConstant;
import com.dz.dzim.config.MsgConfig;
import com.dz.dzim.mapper.MeetingActorDao;
import com.dz.dzim.mapper.MeetingDao;
import com.dz.dzim.pojo.doman.MeetingActorEntity;
import com.dz.dzim.pojo.doman.MeetingEntity;
import com.dz.dzim.service.MainMeeting;
import com.dz.dzim.service.Meeting;
import com.dz.dzim.service.MeetingActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 会场的基础类
 *
 * @author qianyangdong
 */
@Service
public abstract class MeetingBase implements Meeting {
    private final String mId = UUID.randomUUID().toString(); //会场唯一编号

    private final Map<String, MeetingActorImpl> mActors = new HashMap();
    public static final String WAITER_U = "waiter";
    public static final String MEMBER_U = "member";

    @Autowired
    private MsgConfig msgConfig;

    @Autowired
    private MeetingDao meetingDao;

    @Autowired
    private MeetingActorDao meetingActorDao;

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
    public void closedActor(String userId, String userType, String meetingId) throws Exception {
        MeetingActorImpl actor;
        synchronized (this.mActors) {
            String meetingType = this.getType();
            actor = this.mActors.get(userId);
            if (actor != null) {
                this.mActors.remove(userId);
            }
            //如果主会场 上线下线 则更新代抢列表
            if (meetingType.equals(MainMeeting.MAIN_MEETING)) {
                Map<String, Object> map = this.getActorsByMainWaiter();
                this.waitingList("0x60", map);
            }
            //如果小会场 上线下线
            if (meetingType.equals(SmallMeetingImpl.SMALL_MEETING)) {
                List<MeetingActorEntity> actorDaoList = meetingActorDao.selectList(new QueryWrapper<>(new MeetingActorEntity(meetingId, SysConstant.ZERO)));

                switch (userType) {
                    //如果下线的是小会场客服
                    case WAITER_U:
                        meetingDao.updateById(new MeetingEntity(meetingId, new Date(), SysConstant.STATUS_THREE, SysConstant.TWO));
                        meetingActorDao.update(null, new UpdateWrapper<MeetingActorEntity>().eq("meetingId", meetingId).set("is_leaved", SysConstant.TWO));
                        break;
                    //如果下线的是小会场用户
                    case MEMBER_U: //如果下线的是小会场用户
                        meetingActorDao.update(null, new UpdateWrapper<MeetingActorEntity>().eq("meetingId", meetingId).eq("talker", userId).eq("talker_type", userType).set("is_leaved", SysConstant.TWO));
                        MeetingEntity meetingEntity = meetingDao.selectById(meetingId);
                        Calendar rightNow = Calendar.getInstance();
                        rightNow.setTime(new Date());
                        rightNow.add(Calendar.MINUTE, msgConfig.getSpareTimeend());
                        meetingEntity.setSpareTimeend(rightNow.getTime());
                        meetingEntity.setClosedReason(SysConstant.STATUS_THREE); //当前小会场还剩一个人在线
                        meetingDao.updateById(meetingEntity);
                        // 用户下线 通知小会场的客服
                        AtomicReference<Long> waiter = null;
                        //1.如果客户直接关闭了聊天窗口，客服后台那边会提示客户已下线，会话已结束
                        if (MEMBER_U.equals(userType)) {
                            actorDaoList.stream().forEach(l -> {
                                if (l.getTalkerType().equals(WAITER_U)) {
                                    waiter.set(l.getTalker());
                                }
                            });
                        }
                        this.getActor(String.valueOf(waiter.get())).
                                getWebscoket().sendMessage(ResultWebSocket.txtMsgContentToString("0x26", this.mSerialSeed, "用户：" + userId + "离开了小会场"));
                        break;
                    default:
                        break;
                }


            }


        }
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
    public Map<String, Object> getActorsByMainWaiter() throws Exception {
        List list = new ArrayList();
        List listSession = new ArrayList();
        Map<String, Object> map = new HashMap<>();
        synchronized (this.mActors) {
            Map<String, MeetingActorImpl> mActors = this.mActors;
            Set<String> strings = mActors.keySet();
            for (String s : strings) {
                MeetingActorImpl meetingActor = mActors.get(s);
                if (mActors.get(s).getUserType().equals("member")) {
                    JSONObject jsonObject = new JSONObject();
                    String id = meetingActor.getId();
//                    if(!StringUtils.isEmpty(id)){
                    jsonObject.put("id", meetingActor.getId());
                    jsonObject.put("userType", meetingActor.getUserType());
                    list.add(jsonObject);
                    //  }
                } else {
                    WebSocketSession webscoket = meetingActor.getWebscoket();
                    listSession.add(webscoket);
                }

            }
            map.put("memberList", list);
            map.put("waiterSessionList", listSession);

        }
        return map;
    }

    @Override
    public void waitingList(String type, Map<String, Object> map) throws Exception {
        List memberList = (List) map.get("memberList");
        List<WebSocketSession> waiterSessionList = (List<WebSocketSession>) map.get("waiterSessionList");
        if (waiterSessionList.size() > 0 && null != waiterSessionList) {
            JSONObject json = new JSONObject();
            json.put("type", type);
            json.put("sTime", System.currentTimeMillis());
            json.put("serial", this.nextSerial());
            json.put("content", memberList);
            TextMessage textMessage = new TextMessage(JSONObject.toJSONString(json));
            for (WebSocketSession session : waiterSessionList) {
                session.sendMessage(textMessage);
            }
        }
    }


    @Override
    public void pingSoket(String type, WebSocketSession webSocketSession) throws Exception {
        webSocketSession.sendMessage(ResultWebSocket.txtMsgContentToString(type, this.mSerialSeed, "ok"));
    }

    private long mSerialSeed = 0; //消息流水号的种子

    /**
     * 取下一个可用的消息流水号
     *
     * @return 消息流水号
     */
    public final long nextSerial() {
        synchronized (this) {
            return ++this.mSerialSeed;
        }
    }

}
