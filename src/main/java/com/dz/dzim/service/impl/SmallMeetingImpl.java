package com.dz.dzim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dz.dzim.common.ResultWebSocket;
import com.dz.dzim.common.SysConstant;
import com.dz.dzim.config.MsgConfig;
import com.dz.dzim.config.SpringContextUtil;
import com.dz.dzim.mapper.MeetingActorDao;
import com.dz.dzim.mapper.MeetingDao;
import com.dz.dzim.pojo.doman.MeetingActorEntity;
import com.dz.dzim.pojo.doman.MeetingEntity;
import com.dz.dzim.service.SmallMeeting;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SmallMeetingImpl extends MeetingBase implements SmallMeeting {

    private MsgConfig msgConfig = (MsgConfig) SpringContextUtil.getApplicationContext().getBean("msgConfig");

    private MeetingDao meetingDao = (MeetingDao) SpringContextUtil.getApplicationContext().getBean("meetingDao");
    private MeetingActorDao meetingActorDao = (MeetingActorDao) SpringContextUtil.getApplicationContext().getBean("meetingActorDao");



    public SmallMeetingImpl(MeetingControlImpl control) {
    }

    @Override
    public String getType() throws Exception {
        return SMALL_MEETING;
    }

    @Override
    public void remodeActor(String userId, String userType, String meetingId) throws Exception {
        List<MeetingActorEntity> actorDaoList = meetingActorDao.selectList(new QueryWrapper<>(new MeetingActorEntity(meetingId, SysConstant.ZERO)));
        switch (userType) {
            //如果下线的是小会场客服
            case WAITER_U:
                meetingActorDao.update(null, new UpdateWrapper<MeetingActorEntity>().eq("meetingId", meetingId).set("is_leaved", SysConstant.TWO));
                meetingDao.updateById(new MeetingEntity(meetingId, new Date(), SysConstant.STATUS_THREE, SysConstant.TWO));
                break;
            //如果下线的是小会场用户
            case MEMBER_U: //如果下线的是小会场用户
                meetingActorDao.update(null, new UpdateWrapper<MeetingActorEntity>().eq("meetingId", meetingId).eq("talker", userId).eq("talker_type", userType).set("is_leaved", SysConstant.TWO));
                MeetingEntity meetingEntity = meetingDao.selectById(meetingId);
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(new Date());
                rightNow.add(Calendar.MINUTE, this.msgConfig.getSpareTimeend());
                meetingEntity.setSpareTimeend(rightNow.getTime());
                meetingEntity.setClosedReason(SysConstant.STATUS_THREE); //当前小会场还剩一个人在线
                meetingDao.updateById(meetingEntity);
                // 用户下线 通知小会场的客服
                AtomicReference<Long> talkers = new AtomicReference<>(new Long(0));
                //1.如果客户直接关闭了聊天窗口，客服后台那边会提示客户已下线，会话已结束
                if (MEMBER_U.equals(userType)) {
                    actorDaoList.stream().forEach(l -> {
                        if (l.getTalkerType().equals(WAITER_U)) {
                            talkers.set(l.getTalker());
                          // talkers = l.getTalker();
                        }
                    });
                }
                if(0 !=talkers.get()){
                    this.getActor(String.valueOf(talkers.get())).
                            getWebscoket().sendMessage(
                            ResultWebSocket.txtMsgContentToString(
                                    "0x26", this.nextSerial(),
                                    "用户：" + userId + "离开了小会场"));
                }

                break;
            default:
                break;
        }
    }
}
