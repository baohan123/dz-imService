package com.dz.dzim.service;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.pojo.vo.MsgVo;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 会场的参与者
 *
 * @author qianyangdong
 */
public interface MeetingActor {
    /**
     * 取得参与者编号
     * <p>
     * 目前会场参与者的编号就是会场所关联的用户的编号。
     * 约束是：一个会场对同一个用户只能有一个参与者
     * </p>
     *
     * @return 参与者编号
     * @throws Exception
     */
    public String getId() throws Exception;

    public String getUserType() throws Exception;


    /**
     * 取得其所属的会场
     *
     * @return
     * @throws Exception
     */
    public Meeting getMeeting() throws Exception;

    /**
     * 设置参与者的底层SOCKET链路
     *
     * @param session 表示Socket链路
     * @throws Exception 操作失败
     */
    public void setWebsocket(WebSocketSession session) throws Exception;


    public WebSocketSession getWebscoket() throws Exception;

    /**
     * 发送欢迎辞
     * meetingType 会场类型
     * @throws Exception 操作失败
     */
    public void sayWellcome(String meetingType, String meetingId) throws Exception;

    /**
     * 小会场聊天
     * userId ==>会场发言热
     * meetingId 小会场编号
     *
     * @throws Exception 操作失败
     */
    public void sendMsg(MsgVo msgVo) throws Exception;

    /**
     * 会场类型
     *
     * @param meettingType
     */
    void setMeettingType(String meettingType);

}
