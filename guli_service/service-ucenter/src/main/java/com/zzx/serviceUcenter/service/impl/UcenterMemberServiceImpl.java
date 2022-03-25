package com.zzx.serviceUcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.commonutils.JwtUtils;
import com.zzx.commonutils.MD5;
import com.zzx.serviceUcenter.entity.UcenterMember;
import com.zzx.serviceUcenter.entity.Vo.LoginVo;
import com.zzx.serviceUcenter.entity.Vo.RegisterVo;
import com.zzx.serviceUcenter.mapper.UcenterMemberMapper;
import com.zzx.serviceUcenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.servicebase.handler.zzxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-03-18
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public
    String login(LoginVo loginVo) {
        //获取手机号和密码
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        //判断输入的手机号和密码是否为空
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)){
            try {
                throw new zzxException(20001,"手机号或密码为空");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phone);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null){
            try {
                throw new zzxException(20001,"手机号不存在");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }

        //判断密码是否正确
        // MD5加密是不可逆性的，不能解密，只能加密
        //将获取到的密码经过MD5加密与数据库比较
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            try {
                throw new zzxException(20001,"密码不正确");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }

        //判断用户是否禁用
        if (ucenterMember.getIsDisabled()){
            try {
                throw new zzxException(20001,"用户被禁用");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }

        //生成jwtToken
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    @Override
    public
    void register(RegisterVo registerVo) {
        //获取前端传来的数据
        String nickname = registerVo.getNickname(); //昵称
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String password = registerVo.getPassword(); //密码

        //非空判断
        if (StringUtils.isEmpty(nickname)
                ||StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(password)){
            try {
                throw new zzxException(20001,"传来的数据有空值，注册失败");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }

        //判断验证码
        //获取redis验证码，根据手机号获取
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            try {
                throw new zzxException(20001,"注册失败");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }


        //手机号不能重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>=1){
            try {
                throw new zzxException(20001,"手机号重复，注册失败");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }

        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setPassword(MD5.encrypt(password));//密码加密
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/65423f14-49a9-4092-baf5-6d0ef9686a85.png");
        baseMapper.insert(member);

    }

    @Override
    public
    UcenterMember getMemberByOpenId(String openid) {
        QueryWrapper<UcenterMember> MemberQueryWrapper = new QueryWrapper<>();
        MemberQueryWrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(MemberQueryWrapper);
        return ucenterMember;
    }

    //查询一天中新注册用户数
    @Override
    public
    Integer getCountRegister(String day) {
        Integer count = baseMapper.getCountRegister(day);
        return count;
    }
}
