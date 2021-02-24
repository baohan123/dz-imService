package com.dz.dzim.service;

import java.util.Map;

/**
 * 会场控制器
 *
 * @author qianyangdong
 */
public interface MeetingControl {
    /**
     * 获取主会场
     *
     * @return
     * @throws Exception
     */
    public MainMeeting getMainMeeting() throws Exception;

    /**
     * 根据会场id查询会场  返回
     *
     * @param meetingId
     * @return
     * @throws Exception
     */
    public Meeting getMeetingById(String meetingId) throws Exception;

    /**
     * 根据会场id 查询小会场
     *
     * @param smallmeetingId
     * @return
     * @throws Exception
     */
    public Meeting getMeetingByIdSmall(String smallmeetingId) throws Exception;

    /**
     * 获取会场
     *
     * @param meetingId
     * @return
     * @throws Exception
     */
    public Meeting getMeeting(String meetingId) throws Exception;

    /**
     * 创建小会场 生成会场id
     *
     * @return
     * @throws Exception
     */
    public SmallMeeting createSmallMeeting() throws Exception;

    /**
     * 删除当前小会场
     */
    public void removeSmallMeeting(String smallMeetingId) throws Exception;

}
