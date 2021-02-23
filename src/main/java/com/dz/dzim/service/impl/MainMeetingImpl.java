package com.dz.dzim.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.common.ResultWebSocket;
import com.dz.dzim.service.MainMeeting;
import com.dz.dzim.service.MeetingActor;
import com.dz.dzim.service.MeetingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class MainMeetingImpl extends MeetingBase implements MainMeeting {
	@Autowired
	private MeetingBase meetingBase;
	@Autowired
	private MeetingControl meetingControl;
	/**
	 * 邀请一个主会场的参与者，加入到一个小会场
	 * @param mainMeetingActorId 主会场参与者编号 即主会场用户id
	 * @param smallMeetingId 小会场编号
	 * @throws Exception 操作失败
	 */
	@Override
	public void inviteActorToSmallMeeting(String mainMeetingActorId, String smallMeetingId, WebSocketSession webscoket)
	throws Exception
	{
		String content =smallMeetingId;
		TextMessage textMessage = ResultWebSocket.txtMsgContentToString("0x24", this.nextSerial(), content);
		webscoket.sendMessage(textMessage);
	}
}
