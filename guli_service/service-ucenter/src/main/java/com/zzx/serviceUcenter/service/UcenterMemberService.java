package com.zzx.serviceUcenter.service;

import com.zzx.serviceUcenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.serviceUcenter.entity.Vo.LoginVo;
import com.zzx.serviceUcenter.entity.Vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-18
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录判断
    String login(LoginVo loginVo);

    //注册判断
    void register(RegisterVo registerVo);

    UcenterMember getMemberByOpenId(String openid);
//查询一天中新注册用户数
    Integer getCountRegister(String day);
}
