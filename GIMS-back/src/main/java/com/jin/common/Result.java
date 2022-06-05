package com.jin.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 金磊
 */
@Data
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }

}
