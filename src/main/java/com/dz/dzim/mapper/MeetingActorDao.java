package com.dz.dzim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dz.dzim.pojo.doman.MeetingActorEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天参与者状态记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-29 16:17:36
 */
@Mapper
public interface MeetingActorDao extends BaseMapper<MeetingActorEntity> {
}
