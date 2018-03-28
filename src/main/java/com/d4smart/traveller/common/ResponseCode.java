package com.d4smart.traveller.common;

/**
 * Created by d4smart on 2017/12/9 16:48
 */
public enum ResponseCode {

    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    ILLEGAL_ARGUMENT(2, "参数不合法"),
    PERMISSION_DENIED(4, "没有权限"),
    NEED_LOGIN(10, "请先登录");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
