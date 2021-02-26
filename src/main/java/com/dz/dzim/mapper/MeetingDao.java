package com.dz.dzim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dz.dzim.pojo.doman.MeetingEntity;
import com.dz.dzim.pojo.vo.MeetingAndActorEntityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 小会场的建立和结束记录表
 * com.dz.dzim.mapper.MeetingDao
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-29 11:06:32
 */
@Mapper
@Component
public interface MeetingDao extends BaseMapper<MeetingEntity> {
    @Select("SELECT\n" +
            "\tm.id,\n" +
            "\tm.creat_time,\n" +
            "\tm.spare_timeout,\n" +
            "\tm.state,\n" +
            "\tm.closed_reason,\n" +
            "\ta.talker,\n" +
            "\ta.talker_type,\n" +
            "\ta.join_time,\n" +
            "\ta.leaving_time,\n" +
            "\ta.is_leaved,\n" +
            "\ta.leaved_reason \n" +
            "FROM\n" +
            "\tmeeting m\n" +
            "\tLEFT JOIN meeting_actor a ON m.id = a.meetingId \n" +
            "WHERE m.id= #{id} and m.state= #{state}")
    List<MeetingAndActorEntityVo> findByIdAndActor(String id, Integer state);

    @Select("SELECT id FROM meeting m WHERE m.closed_reason =3 " +
            "and m.spare_timeend < #{date} AND m.state != 3 ")
    List<String> findByClosedReasonAndSpareTimeend(Date date);

    //查询存活的小会场
    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tmeeting \n" +
            "WHERE\n" +
            "\tid = ( SELECT meetingId FROM meeting_actor a WHERE ( a.talker = #{userId} OR a.talker = #{kfWaiterId} ) AND is_leaved = 0 LIMIT 1 ) \n" +
            "\tAND closed_reason = #{closedReason}")
    MeetingEntity getByIdAndCloseReason(Integer closedReason, String userId, String kfWaiterId);
}
