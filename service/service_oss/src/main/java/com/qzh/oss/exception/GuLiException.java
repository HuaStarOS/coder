package com.qzh.oss.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qzh
 * @Date: 2021/3/21 13:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuLiException extends RuntimeException{

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;
}
