package com.mojota.succulent.model;

/**
 * http响应消息
 * Created by mojota on 18-8-1
 */
public class ResponseInfo extends BaseBean {
    private static final long serialVersionUID = 1L;

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
