package com.dz.dzim.pojo.vo;

import com.dz.dzim.pojo.doman.MeetingEntity;

import java.util.Date;

/**
 * @author baohan
 * @className MeetingEntityVo
 * @description 小会场查询返回vo
 * @date 2021/1/29 16:39
 */
public class MeetingAndActorEntityVo extends MeetingEntity {
    //关联表字段
    /**
     * 说话人在其所属类型下的唯一编号
     */
    private Long talker;
    /**
     * 说话人类型：
     客服(Waiter)，客户(Member)，系统（System），智能应答（Robot）
     */
    private String talkerType;
    /**
     * 终端设备标识
     */
    private Long meid;
    /**
     * 外键，所属聊天会场的编号
     */
    private String meetingid;
    /**
     * 会场发起邀请的时间
     */
    private Date inviteTime;
    /**
     * 加入会场的时间
     */
    private Date joinTime;
    /**
     * 离开会场的时间
     */
    private Date leavingTime;
    /**
     * 是否已经离开 ：0-未离开；1-正常离开；2-会场关闭
     */
    private Integer isLeaved;
    /**
     * 离开的原因：0-未离开；1-正常离开；2-会场关闭
     */
    private Integer leavedReason;

    public Long getTalker() {
        return talker;
    }

    public void setTalker(Long talker) {
        this.talker = talker;
    }

    public String getTalkerType() {
        return talkerType;
    }

    public void setTalkerType(String talkerType) {
        this.talkerType = talkerType;
    }

    public Long getMeid() {
        return meid;
    }

    public void setMeid(Long meid) {
        this.meid = meid;
    }

    public String getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(String meetingid) {
        this.meetingid = meetingid;
    }

    public Date getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime) {
        this.inviteTime = inviteTime;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(Date leavingTime) {
        this.leavingTime = leavingTime;
    }

    public Integer getIsLeaved() {
        return isLeaved;
    }

    public void setIsLeaved(Integer isLeaved) {
        this.isLeaved = isLeaved;
    }

    public Integer getLeavedReason() {
        return leavedReason;
    }

    public void setLeavedReason(Integer leavedReason) {
        this.leavedReason = leavedReason;
    }
}
