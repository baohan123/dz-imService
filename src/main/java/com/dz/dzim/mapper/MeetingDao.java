package com.dz.dzim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dz.dzim.pojo.doman.MeetingEntity;
import com.dz.dzim.pojo.vo.MeetingAndActorEntityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

}
