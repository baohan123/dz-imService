package com.dz.dzim.service;

import org.springframework.web.socket.WebSocketSession;

/**
 * 主会场
 * @author qianyangdong
 */
public interface MainMeeting extends Meeting
{
	public static final String MAIN_MEETING = "mainMeeting";

	/**
	 * 邀请一个主会场的参与者，加入到一个小会场
	 * @param mainMeetingActorId 主会场参与者编号
	 * @param smallMeetingId 小会场编号
	 * @throws Exception 操作失败
	 */
	public void inviteActorToSmallMeeting(String mainMeetingActorId, String smallMeetingId)
	throws Exception;

}
