package com.zzx.ordservice.service;

import com.zzx.ordservice.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-21
 */
public interface TPayLogService extends IService<TPayLog> {
//根据id生成二维码
    Map<String, Object> createWxQrcode(String orderNo);
//查询是否支付成功
    Map<String, String> queryPayStatus(String orderNo);
//添加支付记录
    void updateOrderStatus(Map<String, String> map);
}
