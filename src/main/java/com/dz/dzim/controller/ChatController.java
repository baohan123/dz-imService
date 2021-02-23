package com.dz.dzim.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dz.dzim.common.SysConstant;
import com.dz.dzim.common.inter.CodeEnum;
import com.dz.dzim.mapper.MeetingChattingDao;
import com.dz.dzim.pojo.doman.MeetingChattingEntity;
import com.dz.dzim.pojo.vo.QueryParams;
import com.dz.dzim.pojo.vo.ResponseVO;
import com.dz.dzim.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private MeetingControl mMeetingControl;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private MeetingChattingDao meetingChattingDao;

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
        //    mainMeeting.inviteActorToSmallMeeting(mainMeetingActorId, smallMeetingId);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("==>smallMeetingId =" + smallMeetingId+e.getMessage());
            return new ResponseVO(CodeEnum.CREATION);
        }
        return new ResponseVO(smallMeetingId);
    }

    /**
     * 上传图片
     * @param file  文件
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload/img")
    public ResponseVO uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String imgsrc = uploadService.uploadImage(file, request);
        if (StringUtils.isNotBlank(imgsrc)) {
            return new ResponseVO(imgsrc);
        }
        return new ResponseVO("上传失败！");
    }

    /**
     * 分页查询聊天记录
     */
    @PostMapping("/queryChat")
    public ResponseVO queryChat(@RequestBody QueryParams params) {
        Long startTime = params.getStartTime();
        Long endTime = params.getEndTime();
        if (null == endTime || SysConstant.ZERO == endTime) {
            endTime = System.currentTimeMillis();
        }

        Page<MeetingChattingEntity> page = QueryParams.getPage(params);
        Long talker = params.getTalker();
        //如果是用户
        QueryWrapper<MeetingChattingEntity> queryWrapper = new QueryWrapper();
        //条件查询
        queryWrapper.eq("talker", talker).or().eq("addr_id", talker).orderByDesc("server_time");
        if (null != startTime && SysConstant.ZERO != startTime) {
            Long finalEndTime = endTime;
            queryWrapper.and(wrapper -> wrapper.ge("server_time", startTime).le("server_time", finalEndTime)) ;
        }
        Page<MeetingChattingEntity> meetingChattingEntityPage = meetingChattingDao.selectPage(page, queryWrapper);
        return new ResponseVO(meetingChattingEntityPage);
    }
}
