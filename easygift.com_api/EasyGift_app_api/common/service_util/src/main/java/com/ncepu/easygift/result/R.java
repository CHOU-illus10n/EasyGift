package com.ncepu.easygift.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
public class R {
    private Integer code;
    private Boolean success;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();

    private R() {}

    public static R ok() {
        R r = new R();
        r.code = REnum.SUCCESS.getCode();
        r.success = REnum.SUCCESS.isSuccess();
        r.message = REnum.SUCCESS.getMessage();
        return r;
    }

    public static R error() {
        R r = new R();
        r.code = REnum.ERROR.getCode();
        r.success = REnum.ERROR.isSuccess();
        r.message = REnum.ERROR.getMessage();
        return r;
    }

    // 链式调用
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }
}
