package com.qzh.educenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.educenter.common.JwtUtils;
import com.qzh.educenter.common.MD5;
import com.qzh.educenter.common.Result;
import com.qzh.educenter.entity.UcenterMember;
import com.qzh.educenter.entity.vo.LoginVo;
import com.qzh.educenter.entity.vo.Password;
import com.qzh.educenter.entity.vo.RegisterVo;
import com.qzh.educenter.entity.vo.UcenterMemberVo;
import com.qzh.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author qzh
 * @since 2022-04-05
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录接口
     * @param loginVo
     * @return
     */
    @PostMapping("/login")
    public Result loginUser(@RequestBody LoginVo loginVo) {
        // 调用service方法实现登录
        // 返回token值，使用jwt生成
        return memberService.login(loginVo);
    }


    /**
     * 注册接口
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo) {
        return memberService.register(registerVo);
    }

    @PostMapping("/retrieve")
    public Result retrieve(@RequestBody RegisterVo registerVo) {
        return memberService.retrieve(registerVo);
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("/getUserInfoForJwt")
    public Result getUserInfoForJwt(HttpServletRequest request) {
        //调用jwt工具类里面的根据request对象，获取头信息，返回用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库，根据用户id，获取用户信息
        UcenterMember member = memberService.getById(id);
        return Result.ok().data("userInfo", member);
    }

    //根据用户id查询用户信息
    @PostMapping("/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String memberId){
        UcenterMember member = memberService.getById(memberId);
        UcenterMemberVo memberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member,memberVo);
        return memberVo;
    }

    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody UcenterMember ucenterMember,HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        ucenterMember.setId(memberId);
        ucenterMember.setGmtModified(new Date());
        memberService.updateById(ucenterMember);
        return Result.ok().message("修改成功");
    }

    @PostMapping("update/password")
    public Result updatePassword(@RequestBody Password password, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember password1 = memberService.getOne(new QueryWrapper<UcenterMember>()
                .eq("id",memberId)
                .eq("password", MD5.encrypt(password.getOldpassword())));
        if(password1==null){
            return Result.error().message("原密码错误");
        }else {
            UcenterMember ucenterMember = new UcenterMember();
            ucenterMember.setId(memberId);
            System.out.println(memberId);
            ucenterMember.setPassword(MD5.encrypt(password.getNewpassword()));
            ucenterMember.setGmtModified(new Date());
            memberService.updateById(ucenterMember);
            return Result.ok().message("修改成功");
        }

    }

}

