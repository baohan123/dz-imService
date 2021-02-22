package com.dz.dzim.common;/**
 * @description: some desc
 * @author: lenovo
 * @email: xxx@xx.com
 * @date: 2021/1/25 11:33
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author baohao
 * @className StringUtilsCom
 * @description TODO
 * @date 2021/1/25 11:33
 */
public class GeneralUtils {

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
     * @param num
     * @return
     */
    public static String randomUUID(int num) {
        // Random random = new Random(10);
        long l = System.currentTimeMillis();
        return String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString().substring(num).replace("-", "");
    }


    /**
     * 将Map序列化为字符串
     */
    public static String serializeMap(Map<String , Object> map){
        return new JSONObject(map).toString();
    }
    /**
     * 将Map序列化为字符串
     */
    public static String serializeList(List<Object> list){
        return new JSONArray(list).toString();
    }

    public static String objectToString(String type,Object obj){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        jsonObject.put("obj",obj);

        return jsonObject.toJSONString();
    }

}
