package com.weim.utils;

/**
 * @author weim
 * @date 18-6-28
 */

public class ResObject {
    public Integer code = 0;
    public String msg = "ok";
    public Object content;

    public ResObject(Object content) {
        this.content = content;
    }

    public ResObject(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResObject() {}
}
