package com.zzx.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient {

    //根据【用户id、课程id】查询订单表中的状态
    @GetMapping("/ordservice/order/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable("memberId") String memberId, @PathVariable("courseId") String courseId);


}

