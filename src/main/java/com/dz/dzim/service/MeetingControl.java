package com.dz.dzim.service;

/**
 * 会场控制器
 * @author qianyangdong
 */
public interface MeetingControl
{
	public MainMeeting getMainMeeting() throws Exception;
	
	public Meeting getMeetingById(String meetingId) throws Exception;

	public SmallMeeting createSmallMeeting() throws Exception;
}
