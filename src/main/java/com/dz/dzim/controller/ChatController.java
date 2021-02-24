package com.dz.dzim.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dz.dzim.common.GeneralUtils;
import com.dz.dzim.common.SysConstant;
import com.dz.dzim.common.inter.CodeEnum;
import com.dz.dzim.mapper.MeetingActorDao;
import com.dz.dzim.mapper.MeetingChattingDao;
import com.dz.dzim.mapper.MeetingDao;
import com.dz.dzim.mapper.MeetingPlazaDao;
import com.dz.dzim.pojo.doman.MeetingActorEntity;
import com.dz.dzim.pojo.doman.MeetingChattingEntity;
import com.dz.dzim.pojo.doman.MeetingEntity;
import com.dz.dzim.pojo.vo.QueryParams;
import com.dz.dzim.pojo.vo.ResponseVO;
import com.dz.dzim.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private MeetingControl mMeetingControl;

    @Autowired
    private MeetingDao meetingDao;

    @Autowired
    private MeetingActorDao meetingActorDao;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private MeetingChattingDao meetingChattingDao;

    @Autowired
    private MeetingPlazaDao meetingPlazaDao;

    /**
     * 创建大会场   mainMeetingActorId(userId)    mainMeeingId
     */
    @PostMapping("/creatMainMeeting")
    public ResponseVO creatMainMeeting(@RequestBody JSONObject jsonObject) {
        Meeting mainMeeting = null;
        String mainMeetingId = null;
        String userType = jsonObject.getString("userType");
        try {
            mainMeeting = mMeetingControl.getMainMeeting();
            mainMeetingId = mainMeeting.getId();
            return new ResponseVO(mainMeetingId);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new ResponseVO(CodeEnum.CREATION);
        }

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
            MeetingEntity meetingEntity = new MeetingEntity(smallMeetingId, new Date(), SysConstant.ZERO, new Date());
            meetingDao.insert(meetingEntity);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("==>smallMeetingId =" + smallMeetingId + e.getMessage());
            return new ResponseVO(CodeEnum.CREATION);
        }
        return new ResponseVO(smallMeetingId);
    }

    /**
     * 上传图片
     *
     * @param file    文件
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
     * 分页 查询当前用户所有聊天记录
     */
    @PostMapping("/queryChatToUser")
    public ResponseVO queryChatToUser(@RequestBody JSONObject jsonObject) {
        Long talker = jsonObject.getLong("member");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        //select * from chatting
        // where meetingid in (select distinct(meetingId)  from chatting where talker='me');
        // params.getStartTime();
        Date startTimeDate = GeneralUtils.timeStamp2Date(startTime);
        Date endTimeDate = GeneralUtils.timeStamp2Date(endTime);
        //分页信息
        PageHelper.startPage(pageNum, pageSize);
        //执行分页查询
        PageInfo<MeetingChattingEntity> userInfoPage = new PageInfo<MeetingChattingEntity>(
                meetingChattingDao.findByMeetingIdChatt(startTimeDate, endTimeDate, talker));
        return new ResponseVO(userInfoPage);
    }


    /**
     * 分页查询聊天记录  当前客服，用户 一对一聊天记录
     */
    @PostMapping("/queryChatToWaiter")
    public ResponseVO queryChatToWaiter(@RequestBody QueryParams params) {
//        Long startTime = params.getStartTime();
//        Long endTime = params.getEndTime();
//        if (null == endTime || SysConstant.ZERO == endTime) {
//            endTime = System.currentTimeMillis();
//        }
//        Page<MeetingChattingEntity> page = QueryParams.getPage(params);
//        Long memberId = params.getMember();
//        Long waiter = params.getWaiter();
//        QueryWrapper<MeetingChattingEntity> queryWrapper = new QueryWrapper();
//
//        queryWrapper.eq("talker", waiter).eq("addr_id", memberId).orderByDesc("server_time");
//        if (null != startTime && SysConstant.ZERO != startTime) {
//            Long finalEndTime = endTime;
//            queryWrapper.and(wrapper -> wrapper.ge("server_time", startTime).le("server_time", finalEndTime));
//        }
//        Page<MeetingChattingEntity> meetingChattingEntityPage = meetingChattingDao.selectPage(page, queryWrapper);
//        return new ResponseVO(meetingChattingEntityPage);
        return null;
    }

    /**
     * 分页查询聊天记录  当前用户所有聊天记录
     */
    @PostMapping("/queryChatToWaiterList")
    public ResponseVO queryChatToWaiterList(@RequestBody QueryParams params) {

        return null;
    }


}
