package com.qzh.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.educenter.common.JwtUtils;
import com.qzh.educenter.common.MD5;
import com.qzh.educenter.common.Result;
import com.qzh.educenter.entity.UcenterMember;
import com.qzh.educenter.entity.vo.LoginVo;
import com.qzh.educenter.entity.vo.RegisterVo;
import com.qzh.educenter.exception.CoderException;
import com.qzh.educenter.mapper.UcenterMemberMapper;
import com.qzh.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author qzh
 * @since 2022-04-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    /**
     * 登录方法的实现
     * @param member
     * @return
     */
    @Override
    public Result login(LoginVo member) {
        //获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //判断输入的手机号和密码是否为空
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)){
            return Result.error().message("手机号或密码为空");
            // throw new CoderException(20001,"手机号或密码为空");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null){
            return Result.error().message("手机号不存在");
            //throw new CoderException(20001,"手机号不存在");
        }

        // 判断密码是否正确
        // MD5加密是不可逆性的，不能解密，只能加密
        //将获取到的密码经过MD5加密与数据库比较
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            return Result.error().message("密码不正确");
            //throw new CoderException(20001,"密码不正确");
        }

        //判断用户是否禁用
        if (ucenterMember.getIsDisabled()){
            return Result.error().message("用户被禁用");
            // throw new CoderException(20001,"用户被禁用");
        }

        //生成jwtToken
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return Result.ok().data("token", token);
    }

    /**
     * 注册方法
     * @param registerVo
     */
    @Override
    public Result register(RegisterVo registerVo) {
        //获取前端传来的数据
        //昵称
        String nickname = registerVo.getNickname();
        //验证码
        String code = registerVo.getCode();
        //手机号
        String mobile = registerVo.getMobile();
        //密码
        String password = registerVo.getPassword();

        //非空判断
        if (StringUtils.isEmpty(nickname)
                ||StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(password)){
            return Result.error().message("传来的数据有空值，注册失败");
            //throw new CoderException(20001,"传来的数据有空值，注册失败");
        }

        //判断验证码
        //获取redis验证码，根据手机号获取
//        String redisCode = redisTemplate.opsForValue().get(mobile);
//        if (!code.equals(redisCode)){
//            throw new AchangException(20001,"注册失败");
//        }
        // 阿里云没审核通过，就先直接定死一个验证码好了
        if (!"7777".equals(code)){
            return Result.error().message("验证码不正确，注册失败");
            //throw new CoderException(20001,"验证码不正确，注册失败");
        }

        //手机号不能重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>=1){
            return Result.error().message("手机号重复，注册失败");
            //throw new CoderException(20001,"手机号重复，注册失败");
        }

        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        //密码加密
        member.setPassword(MD5.encrypt(password));
        member.setMobile(mobile);
        member.setNickname(nickname);
        //用户不禁用
        member.setIsDisabled(false);
        member.setAvatar("https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/65423f14-49a9-4092-baf5-6d0ef9686a85.png");
        member.setGmtCreate(new Date());
        member.setGmtModified(new Date());
        baseMapper.insert(member);
        return Result.ok();
    }

    @Override
    public Result retrieve(RegisterVo registerVo) {
        //获取前端传来的数据
        //验证码
        String code = registerVo.getCode();
        //手机号
        String mobile = registerVo.getMobile();
        //密码
        String password = registerVo.getPassword();

        //非空判断
        if (StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(password)){
            return Result.error().message("传来的数据有空值，重置密码失败");
            //throw new CoderException(20001,"传来的数据有空值，注册失败");
        }

        //判断验证码
        //获取redis验证码，根据手机号获取
//        String redisCode = redisTemplate.opsForValue().get(mobile);
//        if (!code.equals(redisCode)){
//            throw new AchangException(20001,"注册失败");
//        }
        // 阿里云没审核通过，就先直接定死一个验证码好了
        if (!"7777".equals(code)){
            return Result.error().message("验证码不正确，重置密码失败");
            //throw new CoderException(20001,"验证码不正确，注册失败");
        }

        //手机号不能重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count==0){
            return Result.error().message("手机号不存在，请重新输入");
            //throw new CoderException(20001,"手机号重复，注册失败");
        }

        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        //密码加密
        member.setPassword(MD5.encrypt(password));
        member.setGmtModified(new Date());
        baseMapper.update(member,wrapper);
        return Result.ok();
    }
}
