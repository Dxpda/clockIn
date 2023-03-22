//开发时间 : 2023/2/1 15:44

package com.da.clockin.common.util;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class Result<T> {
    private Integer status;
    private String msg;
    private Boolean success;
    private T data;
    private String time;


    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.data = object;
        result.status = 200;
        result.success = true;
        result.msg = "";
        result.time = DateUtil.now();
        return result;
    }
    public static <T> Result<T> success(T object,String msg) {
        Result<T> result = new Result<>();
        result.data = object;
        result.status = 200;
        result.success = true;
        result.msg = msg;
        result.time = DateUtil.now();
        return result;
    }

    public static <T> Result<T> error(String msg){
        Result<T> result = new Result<>();
        result.msg = msg;
        result.data = null;
        result.status = 404;
        result.success = false;
        result.time = DateUtil.now();
        return result;
    }
    public static <T> Result<T> error(String msg,Integer status){
        Result<T> result = new Result<>();
        result.msg = msg;
        result.data = null;
        result.status = status;
        result.success = false;
        result.time = DateUtil.now();
        return result;
    }

}
