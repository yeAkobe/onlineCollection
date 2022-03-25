package com.zzx.ordservice.service.impl;

import com.zzx.commonutils.vo.EduCourseVo;
import com.zzx.commonutils.vo.UcenterMemberVo;
import com.zzx.ordservice.client.ServiceEduClient;
import com.zzx.ordservice.client.ServiceUcenterClient;
import com.zzx.ordservice.entity.TOrder;
import com.zzx.ordservice.mapper.TOrderMapper;
import com.zzx.ordservice.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.ordservice.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-03-21
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    //远程调用serviceEdu
    @Autowired
    ServiceEduClient serviceEduClient;

    //远程调用serviceUcenter
    @Autowired
    ServiceUcenterClient serviceUcenterClient;
    @Override
    public
    String createOrders(String courseId, String memberId) {

        //根据用户id，获取用户信息
        UcenterMemberVo memberInfo = serviceUcenterClient.getMemberInfoById(memberId);

        //根据课程id，获取课程信息
        EduCourseVo courseInfo = serviceEduClient.getCourseInfoByIdOrder(courseId);

        TOrder tOrder = new TOrder();
        tOrder.setMobile(memberInfo.getMobile());
        tOrder.setNickname(memberInfo.getNickname());
        tOrder.setMemberId(memberId);
        tOrder.setCourseCover(courseInfo.getCover());
        tOrder.setCourseId(courseId);
        tOrder.setCourseTitle(courseInfo.getTitle());
        tOrder.setTeacherName(courseInfo.getTeacherName());
        tOrder.setTotalFee(courseInfo.getPrice());
        tOrder.setStatus(0);//支付状态：（ 0：已支付，1：未支付 ）
        tOrder.setPayType(1);//支付类型： 1：微信 ， 2：支付宝
        tOrder.setOrderNo(OrderNoUtil.getOrderNo()); //订单号

        //保存订单
        baseMapper.insert(tOrder);
        String s = tOrder.getOrderNo();
        //返回订单号
        return tOrder.getOrderNo();
    }

}
