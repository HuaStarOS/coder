package com.qzh.oss.common;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qzh
 * @Date: 2021/3/20 15:15
 * 统一返回结果的类
 */
@Data
public class Result {

    @ApiModelProperty("是否成功")
    private boolean success;

    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 无参构造方法私有
     */
    private Result(){}

    /**
     * 成功静态方法
     * @return
     */
    public static Result ok(){
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS);
        result.setSuccess(true);
        result.setMessage("成功");
        return result;
    }

    /**
     * 失败静态方法
     * @return
     */
    public static Result error(){
        Result result = new Result();
        result.setCode(ResultCode.ERROR);
        result.setSuccess(false);
        result.setMessage("失败");
        return result;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }


}
