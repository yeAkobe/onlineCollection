package com.zzx.eduservice.client;

import com.zzx.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @PostMapping("/serviceUcenter/ucenter-member/getMemberInfoById/{memberId}")
    public
    UcenterMemberVo getMemberInfoById(@PathVariable String memberId);

}


