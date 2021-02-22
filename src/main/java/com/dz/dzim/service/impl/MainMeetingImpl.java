package com.dz.dzim.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.common.ResultWebSocket;
import com.dz.dzim.service.MainMeeting;
import com.dz.dzim.service.MeetingActor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

@Service
public class MainMeetingImpl extends MeetingBase implements MainMeeting {
	/**
	 * 邀请一个主会场的参与者，加入到一个小会场
	 * @param mainMeetingActorId 主会场参与者编号 即主会场用户id
	 * @param smallMeetingId 小会场编号
	 * @throws Exception 操作失败
	 */
	@Override
	public void inviteActorToSmallMeeting(String mainMeetingActorId, String smallMeetingId)
	throws Exception
	{
		JSONObject  content = new JSONObject();
		content.put("smallMeetingId", smallMeetingId);
		content.put("mainMeetingActorId", mainMeetingActorId);
		content.put("msg","邀请加入到一个小会场--->");
		TextMessage textMessage = ResultWebSocket.txtMsg("0x23", this.nextSerial(), content);
		MeetingActor actor = this.getActor(mainMeetingActorId);
		actor.getWebscoket().sendMessage(textMessage);
	}
}
