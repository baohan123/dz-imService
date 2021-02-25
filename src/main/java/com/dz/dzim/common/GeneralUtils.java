package com.dz.dzim.common;/**
 * @description: some desc
 * @author: lenovo
 * @email: xxx@xx.com
 * @date: 2021/1/25 11:33
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dz.dzim.controller.ChatController;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author baohao
 * @className StringUtilsCom
 * @description TODO
 * @date 2021/1/25 11:33
 */
public class GeneralUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);


    public static boolean isStringLenth(String[] s) {
        if (null == s || s.length == 0 || "".equals(s)) {
            return false;
        }
        if (s.length > 0) {
            if (null == s[0] || "".equals(s[0])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成随机数  时间戳+UUID
     *
     * @param num
     * @return
     */
    public static String randomUUID(int num) {
        long l = System.currentTimeMillis();
        return System.currentTimeMillis() + UUID.randomUUID().toString().substring(num).replace("-", "");
    }


    /**
     * 将Map序列化为字符串
     */
    public static String serializeMap(Map<String, Object> map) {
        return new JSONObject(map).toString();
    }

    /**
     * 将Map序列化为字符串
     */
    public static String serializeList(List<Object> list) {
        return new JSONArray(list).toString();
    }

    public static String objectToString(String type, Object obj) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("obj", obj);

        return jsonObject.toJSONString();
    }

    /**
     * 时间戳转date
     *
     * @param time
     * @return
     */
    public static Date timeStamp2Date(String time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            LOGGER.error("时间转换异常" + e.getMessage());
            return null;
        }

    }


}
