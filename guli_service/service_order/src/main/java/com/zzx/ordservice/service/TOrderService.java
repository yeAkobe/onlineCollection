package com.zzx.ordservice.service;

import com.zzx.ordservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-21
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String courseId, String memberId);
}
