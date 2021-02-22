package com.dz.dzim.controller;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.common.enums.inter.Code;
import com.dz.dzim.common.inter.CodeEnum;
import com.dz.dzim.config.MyWebSocketHandler;
import com.dz.dzim.pojo.vo.ResponseVO;
import com.dz.dzim.service.MainMeeting;
import com.dz.dzim.service.Meeting;
import com.dz.dzim.service.MeetingControl;
import com.dz.dzim.service.SmallMeeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private MeetingControl mMeetingControl;

    /**
     * 创建大会场   mainMeetingActorId(userId)    mainMeeingId
     */
    @PostMapping("/creatMainMeeting")
    public ResponseVO creatMainMeeting(@RequestBody JSONObject jsonObject) {
        Meeting mainMeeting = null;
        String mainMeetingId = null;
        try {
            mainMeeting = mMeetingControl.getMainMeeting();
            mainMeetingId = mainMeeting.getId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new ResponseVO(CodeEnum.CREATION);
        }
        return new ResponseVO(mainMeetingId);
    }


    /**
     * 创建小会场
     */
    @PostMapping("/creatSmallMeeting")
    public ResponseVO creatSmallMeeting(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("memberId");

        //被邀请的客户，其在主会场中的参与者的编号，即：客户的用户编号
        String mainMeetingActorId = userId;
        String smallMeetingId = null;
        try {
            MainMeeting mainMeeting = mMeetingControl.getMainMeeting();

            SmallMeeting smallMeeting = mMeetingControl.createSmallMeeting();
            smallMeetingId = smallMeeting.getId();
            mainMeeting.inviteActorToSmallMeeting(mainMeetingActorId, smallMeetingId);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("==>smallMeetingId =" + smallMeetingId+e.getMessage());
            return new ResponseVO(CodeEnum.CREATION);
        }
        return new ResponseVO(smallMeetingId);
    }
}
