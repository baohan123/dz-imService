package com.dz.dzim.pojo.doman;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author hao
 * @date 2021-01-26 23:54:14
 */

@TableName("msg_records")
public class MsgRecordsEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String image;
	/**
	 * 
	 */
	private String msgType;
	/**
	 * 
	 */
	private String message;
	/**
	 * 
	 */
	private String sendType;
	/**
	 * 
	 */
	private Date sendTime;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String messageId;
	/**
	 * 
	 */
	private String receivers;
	/**
	 * 
	 */
	private String temp1;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	@Override
	public String toString() {
		return "MsgRecordsEntity{" +
				"id=" + id +
				", userId='" + userId + '\'' +
				", username='" + username + '\'' +
				", address='" + address + '\'' +
				", image='" + image + '\'' +
				", msgType='" + msgType + '\'' +
				", message='" + message + '\'' +
				", sendType='" + sendType + '\'' +
				", sendTime=" + sendTime +
				", createTime=" + createTime +
				", messageId='" + messageId + '\'' +
				", receivers='" + receivers + '\'' +
				", temp1='" + temp1 + '\'' +
				'}';
	}
	public MsgRecordsEntity() {

	}
	public MsgRecordsEntity(Long id, String userId, String username, String address, String image, String msgType, String message, String sendType, Date sendTime, Date createTime, String messageId, String receivers, String temp1) {
		this.id = id;
		this.userId = userId;
		this.username = username;
		this.address = address;
		this.image = image;
		this.msgType = msgType;
		this.message = message;
		this.sendType = sendType;
		this.sendTime = sendTime;
		this.createTime = createTime;
		this.messageId = messageId;
		this.receivers = receivers;
		this.temp1 = temp1;
	}
}
