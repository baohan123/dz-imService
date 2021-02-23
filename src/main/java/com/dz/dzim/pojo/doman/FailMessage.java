package com.dz.dzim.pojo.doman;/**
 * @className FailMessage
 * @description TODO
 * @author xxxyyy
 * @date 2021/2/6 10:32
 */

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 *@className FailMessage 失败消息表
 *@description TODO  消费消息失败重试多次后还是失败，需要记录失败消息
 *@author xxxyyy
 *@date 2021/2/6 10:32
 */
@TableName("fail_msg")
public class FailMessage {
    /**
     * 编号
     */
    @TableId
    private Long id;

    private String context;

    private Timestamp createTime;


    public FailMessage(String context, Timestamp createTime) {
        this.context = context;
        this.createTime = createTime;
    }
}