package com.dz.dzim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dz.dzim.pojo.doman.MeetingActorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 聊天参与者状态记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-29 16:17:36
 */
@Mapper
@Component
public interface MeetingActorDao extends BaseMapper<MeetingActorEntity> {

    @Select("UPDATE meeting_actor \n" +
            "\tSET is_leaved = 2 \n" +
            "WHERE\n" +
            "\tmeetingId IN ( SELECT id FROM meeting m WHERE spare_timeend < #{nowDate} AND state != 3 ) \n" +
            "\tAND is_leaved != 2 ")
    void findByClosedReasonAndSpareTimeend(Date nowDate);
}
