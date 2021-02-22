package com.dz.dzim.pojo.vo;

import com.dz.dzim.common.enums.CodeEnum;
import com.dz.dzim.common.enums.inter.Code;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 响应数据结构
 *
 * @author baohan
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO implements Serializable {

    private static final long serialVersionUID = -5327212050370584991L;
    private static final CodeEnum SUCCESS = CodeEnum.SUCCESS;

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应数据
     */
    private Object data;
    /**
     * 响应描述
     */
    private String desc;

    /**
     * 成功响应且带响应数据
     *
     * @param data 响应数据
     */
    public ResponseVO(Object data) {
        this.code = SUCCESS.getCode();
        this.desc = SUCCESS.getDesc();
        this.data = data;
    }

    /**
     * 只带响应code和desc
     *
     * @param code 响应code
     */
    public ResponseVO(Code code) {
        this.code = code.getCode();
        this.desc = code.getDesc();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static CodeEnum getSUCCESS() {
        return SUCCESS;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "code=" + code +
                ", data=" + data +
                ", desc='" + desc + '\'' +
                '}';
    }
}
