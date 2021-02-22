package com.dz.dzim.service.impl;

import com.dz.dzim.service.Meeting;
import com.dz.dzim.service.MeetingControl;
import com.dz.dzim.service.SmallMeeting;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 会场控制器
 * baohan
 */
@Configuration
@EnableAutoConfiguration
@Service
public class MeetingControlImpl implements MeetingControl
{
	private final MainMeetingImpl mMainMeeting = new MainMeetingImpl();

	private final Map<String, SmallMeetingImpl> mSmallMeetings = new HashMap();
	
	@Override
	public MainMeetingImpl getMainMeeting() {
		return mMainMeeting;
	}

	@Override
	public Meeting getMeetingById(String meetingId) throws Exception
	{
		Meeting meeting;
		
		if(mMainMeeting.getId().equals(meetingId))
		{
			meeting = mMainMeeting;
		}
		else
		{
			meeting = mSmallMeetings.get(meetingId);
		}
		return meeting;
	}

	@Override
	public SmallMeeting createSmallMeeting() throws Exception
	{
		SmallMeetingImpl smallMeeting = new SmallMeetingImpl(this);
		String smallMeetingId = smallMeeting.getId();
		synchronized(this.mSmallMeetings)
		{
			this.mSmallMeetings.put(smallMeetingId, smallMeeting);
		}
		return smallMeeting;
	}
}
