package com.dz.dzim.service;

import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.service.impl.MeetingActorImpl;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;

/**
 * 会场
 *
 * @author qianyangdong
 */
public interface Meeting {
    //public boolean isMainMeeting() throws Exception;
    public String getType() throws Exception;

    /**
     * 取得会场的唯一编号
     *
     * @return
     * @throws Exception
     */
    public String getId() throws Exception;

    /**
     * 为一个指定用户创建在会场中的参与者
     *
     * @param userId 用户编号
     * @return 对应的参与者，如果已经存在则直接返回
     * @throws Exception 操作失败
     */
    public MeetingActor createActor(String userId, String userType) throws Exception;

    /**
     * 根据参与者编号，检索对应的参与者
     *
     * @param actorId 参与者编号
     * @return 参与者, 如果对应的参与者不存在，则抛出异常
     * @throws Exception 操作失败
     */
    public MeetingActor getActor(String actorId) throws Exception;

    /**
     * 获取会场所有参与者
     *
     * @return
     * @throws Exception
     */
    public List<MeetingActor> getActors() throws Exception;

    /**
     * 获取会场里所有客服参与者
     *
     * @return
     * @throws Exception
     */
    public Map<String, Object> getActorsByMainWaiter() throws Exception;

    void closedActor(String userId, String userType);

    void waitingList(String type, Map<String, Object> map) throws Exception;
}
