package com.dz.dzim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dz.dzim.pojo.doman.MeetingChattingEntity;
import com.dz.dzim.pojo.vo.MeetingAndActorEntityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
            "\t\ta.join_time > '2020-02-24 16:21:30' \n" +
            "\t\tAND a.join_time < '2021-02-21 12:01:30' \n" +
            "\t\tAND a.talker = '3333' \n" +
            "\tGROUP BY\n" +
            "\t\ta.meetingId \n" +
            "\tORDER BY\n" +
            "\tjoin_time DESC \n" +
            "\t)")
    List<MeetingChattingEntity> findByMeetingIdChatt(Date startTime, Date endTime, Long talker);
}
