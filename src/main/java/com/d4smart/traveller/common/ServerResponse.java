package com.d4smart.traveller.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by d4smart on 2017/12/9 16:44
 */
// 序列化json的时候，如果是null的对象，key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    private ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private ServerResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }
    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 判断响应是否成功
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    /**
     * 将对象转换成json字符串
     */
    @JsonIgnore
    public String toJson() {
        return "{" +
                "\"code\":" + code +
                ",\"msg\":\"" + msg +
                "\"}";
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode, errorMessage);
    }
}
