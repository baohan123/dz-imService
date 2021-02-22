package com.dz.dzim.common.enums;

/**
 * @author baohan
 * @className MessageTypeEnum
 * @description TODO
 * @date 2021/1/28 18:09
 */
public enum MessageTypeEnum {
    SYSTEM,  //代表系统消息
    USER,   //代表用户消息
    KFUSER,// 代表客服消息
    REVOKE, //撤回消息
    ROBOT,  //机器人及其他
    ONLINE, //上线
    OFFLINE,// 下线
    ZYZX,//逐一执行
    ZXLJS,// 客户消息接收策略 最下接受人数有限建立连接
    //消息发送方式
    USETTOCLENT, //用户发送给客服客户端
    CLENTTOUSER, //客户端发送给用户
    SYSYTEMTOUSER, //系统发送至客户
    SYSTEMTOCLENT,//系统发送至客服
    SENDTYPES// 发送类型
}
