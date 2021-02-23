package com.dz.dzim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dz.dzim.pojo.doman.MsgRecordsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface ChatRecordMapper extends BaseMapper<MsgRecordsEntity> {

    public Integer addChatRecord(@Param("charMeg") MsgRecordsEntity charMeg ,
                                 @Param("user_id")String user_id,
                                 @Param("send_time") Timestamp send_time,
                                 @Param("message_id") String  messageId);


    public  List<HashMap<String, Object>> listRecordAllFromDb();

    public List<HashMap<String, Object>>  queryByPage(@Param("username")String username);

    public List<MsgRecordsEntity>  queryByPageReturnCharMegs(String username);

}

