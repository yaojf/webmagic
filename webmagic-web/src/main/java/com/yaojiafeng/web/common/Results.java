package com.yaojiafeng.web.common;

/**
 * Created by yaojiafeng on 2017/6/18 下午9:01.
 */
public class Results {
    private int code;
    private String message;
    private Object data;

    public Results(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
