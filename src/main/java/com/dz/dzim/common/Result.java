package com.dz.dzim.common;/**
 * @description: some desc
 * @author: lenovo
 * @email: xxx@xx.com
 * @date: 2021/1/22 14:10
 */


import java.util.List;
import java.util.Map;

/**
 * @author baohan
 * @className Result
 * @description TODO
 * @date 2021/1/22 14:10
 */
public class Result <T>  {

    private long code;
    private String message;
    private T data;


    public static Result<Map<String, String>> success(Map<String, String> map) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("ok");
        result.setData(map);
         return result;
    }

    public static Result<Object> success(Object o) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("ok");
        result.setData(o);
        return result;
    }

    public static Result<Integer> success(Integer online) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("ok");
        result.setData(String.valueOf(online));
        return result;
    }
    public static Result<Void> success(){
        Result result = new Result();
        result.setCode(200);
        result.setMessage("ok");
        return result;
    }





    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
