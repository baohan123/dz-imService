package com.dz.dzim.service.impl;

import com.dz.dzim.service.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

/**
 * 会场控制器
 * baohan
 */
@Configuration
@EnableAutoConfiguration
@Service
public class MeetingControlImpl implements MeetingControl {

    private final MainMeetingImpl mMainMeeting = new MainMeetingImpl();

    private final Map<String, SmallMeetingImpl> mSmallMeetings = new HashMap();

    @Override
    public MainMeetingImpl getMainMeeting() {
        return mMainMeeting;
    }


    @Override
    public Meeting getMeetingById(String meetingId) throws Exception {
        if (mMainMeeting.getId().equals(meetingId)) {
            //判断为主会场
            return mMainMeeting;
        } else {
            //否则为小会场
            return mSmallMeetings.get(meetingId);
        }
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

    @Override
    public void removeSmallMeeting(String smallMeetingId) throws Exception {
        synchronized (this.mSmallMeetings) {
            this.mSmallMeetings.remove(smallMeetingId);
        }
    }

}
