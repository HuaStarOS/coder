package com.qzh.educenter.service;

import com.qzh.educenter.common.Result;
import com.qzh.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qzh.educenter.entity.vo.LoginVo;
import com.qzh.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author qzh
 * @since 2022-04-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 用户登录
     * @param member
     * @return
     */
    Result login(LoginVo member);

    /**
     *     注册的办法
     * @param registerVo
     */
    Result register(RegisterVo registerVo);

    /**
     *     找回密码的办法
     * @param registerVo
     */
    Result retrieve(RegisterVo registerVo);
}
