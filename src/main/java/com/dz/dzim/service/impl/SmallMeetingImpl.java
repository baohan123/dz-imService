package com.dz.dzim.service.impl;

import com.dz.dzim.service.SmallMeeting;
import org.springframework.stereotype.Service;

@Service
public class SmallMeetingImpl extends MeetingBase implements SmallMeeting
{
	public SmallMeetingImpl(MeetingControlImpl control)
	{
	}

	@Override
	public String getType() throws Exception {
		return SMALL_MEETING;
	}


}
