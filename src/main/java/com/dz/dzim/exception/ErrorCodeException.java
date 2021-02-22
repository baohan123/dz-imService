package com.dz.dzim.exception;


import com.dz.dzim.common.enums.inter.Code;

/**
 * 返回错误码
 *
 * @author yanpanyi
 * @date 2019/3/20
 */
public class ErrorCodeException extends RuntimeException  {

    private Code code;
    private String msg;

    public ErrorCodeException(Code code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Code getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public void setCode(Code code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
