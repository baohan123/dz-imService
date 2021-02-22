package com.dz.dzim.pojo;

import com.dz.dzim.common.enums.MessageTypeEnum;

import java.util.Date;

/**
 * 用户信息
 * @author baohan
 * @date 2021/1/22
 */
public class User {

    private static final long serialVersionUID = 5114506546129512029L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 地址
     */
    private String address;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户状态
     */
    private MessageTypeEnum status;

    /**
     * 上线时间
     */
    private Date onLineDate;
    public User(){
    }



}
