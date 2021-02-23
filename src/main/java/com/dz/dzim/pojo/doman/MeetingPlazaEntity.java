package com.dz.dzim.pojo.doman;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 主会场签到表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-29 11:06:32
 */
@TableName("meeting_plaza")
public class MeetingPlazaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号，自增序列，从1开始取值
     */
    @TableId
    private String id;
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
     * 用户加入主会场的时间
     */
    private Date enterTime;
    /**
     * 用户离开主会场的时间
     */
    private Date leavingTime;
    /**
     * 当前状态：0：未定义；1、等待中；2：交谈中；3：休息中；4：已离开
     */
    private Integer state;
    /**
     * 前一次登录主会场时所分配的ID，0表示这是第一次登录主会场，- 表示无记录
     */
    private String prevId;
    /**
     * 后一次登录主会场时所分配的ID，0表示这是最后一次登录主会场, - 表示无记录
     */
    private String nextId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTalker() {
        return talker;
    }

    public void

    setTalker(Long talker) {
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

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(Date leavingTime) {
        this.leavingTime = leavingTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPrevId() {
        return prevId;
    }

    public void setPrevId(String prevId) {
        this.prevId = prevId;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    @Override
    public String toString() {
        return "MeetingPlazaEntity{" +
                "id=" + id +
                ", talker=" + talker +
                ", talkerType='" + talkerType + '\'' +
                ", meid=" + meid +
                ", enterTime=" + enterTime +
                ", leavingTime=" + leavingTime +
                ", state=" + state +
                ", prevId=" + prevId +
                ", nextId=" + nextId +
                '}';
    }

    public MeetingPlazaEntity(String id, Long talker, String talkerType, Long meid, Date enterTime, Date leavingTime, Integer state, String prevId, String nextId) {
        this.id = id;
        this.talker = talker;
        this.talkerType = talkerType;
        this.meid = meid;
        this.enterTime = enterTime;
        this.leavingTime = leavingTime;
        this.state = state;
        this.prevId = prevId;
        this.nextId = nextId;
    }

    public MeetingPlazaEntity(String id, Long talker, String talkerType){
        this.id = id;
        this.talker = talker;
        this.talkerType = talkerType;
    }

    public MeetingPlazaEntity(Long talker, String talkerType){
        this.talker = talker;
        this.talkerType = talkerType;
    }
    public MeetingPlazaEntity(String id,Integer state,Date leavingTime){
        this.id = id;
        this.state = state;
        this.leavingTime = leavingTime;
    }


}
