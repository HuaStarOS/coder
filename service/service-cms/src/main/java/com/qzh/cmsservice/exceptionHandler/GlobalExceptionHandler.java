package com.qzh.cmsservice.exceptionHandler;

import com.qzh.cmsservice.common.Result;
import com.qzh.cmsservice.exception.GuLiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 * @Author: qzh
 * @Date: 2021/3/20 20:16
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     *  @ExceptionHandler(Exception.class) 指定出现什么异常会执行这个方法,Exception.class表示所有异常都会执行
     *
     *  @ResponseBody    因为他不在Controller中。没有@RestController，所以数据不会返回，需要加@ResponeseBody返回数据
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理。。。");
    }

    /**
     * 特殊异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理。。。");
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(GuLiException.class)
    @ResponseBody
    public Result error(GuLiException e){
        // 如果程序运出现异常，把异常信息输出到文件中
        log.error(e.getMessage());

        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

}

