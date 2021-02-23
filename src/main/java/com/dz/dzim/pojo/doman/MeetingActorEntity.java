package com.dz.dzim.pojo.doman;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天参与者状态记录表
 *
 * @author baohan
 * @date 2021-01-29 16:17:36
 */
@TableName("meeting_actor")
public class MeetingActorEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 说话人在其所属类型下的唯一编号
     */
    private Long talker;
    /**
     * 说话人类型：
     * 客服(Waiter)，客户(Member)，系统（System），智能应答（Robot）
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

    @Override
    public String toString() {
        return "MeetingActorEntity{" +
                "id=" + id +
                ", talker=" + talker +
                ", talkerType='" + talkerType + '\'' +
                ", meid=" + meid +
                ", meetingid='" + meetingid + '\'' +
                ", inviteTime=" + inviteTime +
                ", joinTime=" + joinTime +
                ", leavingTime=" + leavingTime +
                ", isLeaved=" + isLeaved +
                ", leavedReason=" + leavedReason +
                '}';
    }

    public MeetingActorEntity(String meetingid, Integer isLeaved) {
        this.meetingid = meetingid;
        this.isLeaved = isLeaved;
    }

    public MeetingActorEntity(Long id, Long talker, String talkerType, Long meid, String meetingid, Date inviteTime, Date joinTime, Date leavingTime, Integer isLeaved, Integer leavedReason) {
        this.id = id;
        this.talker = talker;
        this.talkerType = talkerType;
        this.meid = meid;
        this.meetingid = meetingid;
        this.inviteTime = inviteTime;
        this.joinTime = joinTime;
        this.leavingTime = leavingTime;
        this.isLeaved = isLeaved;
        this.leavedReason = leavedReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
