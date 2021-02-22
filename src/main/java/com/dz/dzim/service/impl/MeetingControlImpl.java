package com.dz.dzim.service.impl;

import com.dz.dzim.service.Meeting;
import com.dz.dzim.service.MeetingActor;
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
public class MeetingControlImpl implements MeetingControl {
    private static final String MAIN_MEETING = "mainMeeting";
    private static final String SMALL_MEETING = "smallMeeting";
    private final MainMeetingImpl mMainMeeting = new MainMeetingImpl();

    private final Map<String, SmallMeetingImpl> mSmallMeetings = new HashMap();

    @Override
    public MainMeetingImpl getMainMeeting() {
        return mMainMeeting;
    }


    @Override
    public Map<String, Meeting> getMeetingById(String meetingId) throws Exception {
        Map<String, Meeting> meet = new HashMap<>();
        if (mMainMeeting.getId().equals(meetingId)) {
            //判断为主会场
            meet.put(MAIN_MEETING, mMainMeeting);
        } else {
            //否则为小会场
            meet.put(SMALL_MEETING, mSmallMeetings.get(meetingId));
        }
        return meet;
    }

    @Override
    public Meeting getMeetingByIdSmall(String smallmeetingId) throws Exception {
        return mSmallMeetings.get(smallmeetingId);
    }

    @Override
    public Meeting getMeeting(String meetingId) throws Exception {
        Meeting meeting;

        if (mMainMeeting.getId().equals(meetingId)) {
            meeting = mMainMeeting;
        } else {
            meeting = mSmallMeetings.get(meetingId);
        }
        return meeting;
    }

    @Override
    public SmallMeeting createSmallMeeting() throws Exception {
        SmallMeetingImpl smallMeeting = new SmallMeetingImpl(this);
        String smallMeetingId = smallMeeting.getId();
        synchronized (this.mSmallMeetings) {
            this.mSmallMeetings.put(smallMeetingId, smallMeeting);
        }
        return smallMeeting;
    }
}
