package com.qzh.eduservice.controller;

import com.qzh.eduservice.common.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: qzh
 * @Date: 2021/3/28 9:44
 * 什么是跨域问题？
 * 通过一个地址去访间另外一个地址，这个过程中如果有三个地方(访问协议，ip地址，端口号)任何一个不一样，就会产生跨域问题
 * 跨域解决方式
 * （1）在后端接口 controller添加注解（常用）@Cross0rigin 解决跨域
 * （2）使用网关解决（后面讲到）
 */
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin // 解决跨域问题
public class EduLoginController {

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public Result info(){

        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://cdn.jsdelivr.net/gh/HuaStarOS/image/icons/pet1.png");
    }

}
