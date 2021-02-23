package com.dz.dzim.pojo.doman;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * 小会场的建立和结束记录表
 *
 * @author baohan
 * @date 2021-01-29 11:06:32
 */

@TableName("meeting")
public class MeetingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会场编号，由系统自动生成：16位紧凑日期时间+6位顺序号+16位随机ASCII字符
     */
    @TableId
    private String id;
    /**
     * 会场创建时间,系统毫秒时间
     */
    private Date creatTime;
    /**
     * 在第一个参与者进入前，允许持续存在的时间
     */
    private Date waitTimeout;
    /**
     * 第一个参与者必须在这个时间之前加入，否则会场自动关闭
     */
    private Date waitTimeend;
    /**
     * 当只有一个参与者的时候，会场最多允许存在多长时间
     */
    private Date spareTimeout;
    /**
     * 当只有一个参与者的时候，会场将在这个时间点自动关闭
     */
    private Date spareTimeend;
    /**
     * 第一个参与者加入会场的时间
     */
    private Date firstJoinTime;
    /**
     * 最后一个参与者离开的时间
     */
    private Date lastLeaveTime;
    /**
     * 当前状态：0-会场被创建；1-有参与者加入；2-所有参与者离开；3-会场被关闭
     */
    private Integer state;
    /**
     * 会场被关闭的原因：0-未关闭；1-没有任何参与者加入，等待超时；2-所有参与者离开；3-只有一个参与者，空闲超时；4-系统异常关闭
     */
    private Integer closedReason;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getWaitTimeout() {
        return waitTimeout;
    }

    public void setWaitTimeout(Date waitTimeout) {
        this.waitTimeout = waitTimeout;
    }

    public Date getWaitTimeend() {
        return waitTimeend;
    }

    public void setWaitTimeend(Date waitTimeend) {
        this.waitTimeend = waitTimeend;
    }

    public Date getSpareTimeout() {
        return spareTimeout;
    }

    public void setSpareTimeout(Date spareTimeout) {
        this.spareTimeout = spareTimeout;
    }

    public Date getSpareTimeend() {
        return spareTimeend;
    }

    public void setSpareTimeend(Date spareTimeend) {
        this.spareTimeend = spareTimeend;
    }

    public Date getFirstJoinTime() {
        return firstJoinTime;
    }

    public void setFirstJoinTime(Date firstJoinTime) {
        this.firstJoinTime = firstJoinTime;
    }

    public Date getLastLeaveTime() {
        return lastLeaveTime;
    }

    public void setLastLeaveTime(Date lastLeaveTime) {
        this.lastLeaveTime = lastLeaveTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getClosedReason() {
        return closedReason;
    }

    public void setClosedReason(Integer closedReason) {
        this.closedReason = closedReason;
    }

    @Override
    public String toString() {
        return "MeetingEntity{" +
                "id='" + id + '\'' +
                ", creatTime=" + creatTime +
                ", waitTimeout=" + waitTimeout +
                ", waitTimeend=" + waitTimeend +
                ", spareTimeout=" + spareTimeout +
                ", spareTimeend=" + spareTimeend +
                ", firstJoinTime=" + firstJoinTime +
                ", lastLeaveTime=" + lastLeaveTime +
                ", state=" + state +
                ", closedReason=" + closedReason +
                '}';
    }

    public MeetingEntity() {

    }

    public MeetingEntity(String id, Date lastLeaveTime, Integer state, Integer closedReason) {
        this.id = id;
        this.lastLeaveTime = lastLeaveTime;
        this.state = state;
        this.closedReason = closedReason;
    }

    public MeetingEntity(String id, Date creatTime, Date waitTimeout, Date waitTimeend, Date spareTimeout, Date spareTimeend, Date firstJoinTime, Date lastLeaveTime, Integer state, Integer closedReason) {
        this.id = id;
        this.creatTime = creatTime;
        this.waitTimeout = waitTimeout;
        this.waitTimeend = waitTimeend;
        this.spareTimeout = spareTimeout;
        this.spareTimeend = spareTimeend;
        this.firstJoinTime = firstJoinTime;
        this.lastLeaveTime = lastLeaveTime;
        this.state = state;
        this.closedReason = closedReason;
    }

    public MeetingEntity(String id, Date creatTime, Integer state, Date waitTimeend) {
        this.id = id;
        this.creatTime = creatTime;
        this.state = state;
        this.waitTimeend = waitTimeend;

    }

}
