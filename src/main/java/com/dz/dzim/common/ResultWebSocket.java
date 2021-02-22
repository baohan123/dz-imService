package com.dz.dzim.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.socket.TextMessage;

/**
 * @author baohan
 * @className Result websocket
 * @description TODO
 * @date 2021/1/22 14:10
 */
public class ResultWebSocket<T> {

    private Integer returnType;

    private T data;

    private long mSerialSeed = 0; //消息流水号的种子

    /**
     * 取下一个可用的消息流水号
     *
     * @return 消息流水号
     */
    public final long nextSerial() {
        synchronized (this) {
            return ++this.mSerialSeed;
        }
    }

    public static TextMessage txtMsgContentToString(String type, long nextSerial, String content) {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("STime", System.currentTimeMillis());
        json.put("Serial", nextSerial);
        json.put("content", content);
        return new TextMessage(JSONObject.toJSONString(json));
    }



    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
