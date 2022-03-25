package com.zzx.serviceUcenter.mapper;

import com.zzx.serviceUcenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2022-03-18
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //统计一天新注册用户数
    Integer getCountRegister(String day);
}
