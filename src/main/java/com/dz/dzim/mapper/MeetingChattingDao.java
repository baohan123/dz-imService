package com.dz.dzim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dz.dzim.pojo.doman.MeetingChattingEntity;
import com.dz.dzim.pojo.vo.MeetingAndActorEntityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 聊天记录表
 *
 * @author baohan
 * @email
 * @date 2021-01-31 17:24:42
 */
@Mapper
@Component
public interface MeetingChattingDao extends BaseMapper<MeetingChattingEntity> {
    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tmeeting_chatting \n" +
            "WHERE\n" +
            "\tmeeting_id IN (\n" +
            "\tSELECT\n" +
            "\t\tmeetingId \n" +
            "\tFROM\n" +
            "\t\tmeeting_actor a \n" +
            "\tWHERE\n" +
            "\t\ta.join_time > #{startTime} \n" +
            "\t\tAND a.join_time < #{endTime} \n" +
            "\t\tAND a.talker = #{talker} \n" +
            "\tGROUP BY\n" +
            "\t\ta.meetingId \n" +
            "\t) \n" +
            "ORDER BY\n" +
            "\tserver_time DESC")
    List<MeetingChattingEntity> findByMeetingIdChatt(Date startTime, Date endTime, Long talker);

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tmeeting_chatting \n" +
            "WHERE\n" +
            "\tmeeting_id IN ( SELECT meeting_id FROM meeting_chatting WHERE talker = '44444' GROUP BY meeting_id ) \n" +
            "\tAND talker_type = #{talker} \n" +
            "\tAND server_time > #{startTimeDate} \n" +
            "\tAND server_time < #{endTimeDate} \n" +
            "GROUP BY\n" +
            "\ttalker \n" +
            "ORDER BY\n" +
            "\tserver_time DESC")
    List<MeetingChattingEntity> queryChatToUserList(String startTimeDate, String endTimeDate, Long talker);
}
