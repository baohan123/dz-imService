package com.dz.dzim.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dz.dzim.common.SysConstant;
import com.dz.dzim.mapper.MeetingActorDao;
import com.dz.dzim.mapper.MeetingDao;
import com.dz.dzim.pojo.doman.MeetingActorEntity;
import com.dz.dzim.pojo.doman.MeetingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 会场定时任务
 * @className MeetingTask
 * @description TODO
 * @date 2021/2/25 16:41
 */
@Component
public class MeetingTask {

    @Autowired
    private MeetingActorDao meetingActorDao;

    @Autowired
    private MeetingDao meetingDao;

    /**
     * @Scheduled(fixedDelay=5000)当任务执行完毕后1分钟在执行
     * @Scheduled(fixedRate=5000)就是每多次分钟一次，不论你业务执行花费了多少时间
     */

//    @Async("MyThreadPool") //自定义的线程执行
//    @Scheduled(fixedDelay=5000)
//    public void fixedDelayJob(){
//        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+" >>fixedDelay执行....");
//    }
    @Async("MyThreadPool") //自定义的线程执行
    @Scheduled(fixedDelay = 50000)
    public void fixedRateJob() {
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + " >>fixedRate执行....");
        //查询小会场仅有一个人在线的用户
        List<String> ids = meetingDao.findByClosedReasonAndSpareTimeend(new Date());
        for (String id : ids) {
            meetingDao.updateById(new MeetingEntity(id, new Date(), SysConstant.STATUS_THREE, SysConstant.TWO));
            meetingActorDao.update(null, new UpdateWrapper<MeetingActorEntity>().set("is_leaved", SysConstant.TWO).
                    eq("meetingId", id).ne("is_leaved", SysConstant.TWO));
        }


    }
}
