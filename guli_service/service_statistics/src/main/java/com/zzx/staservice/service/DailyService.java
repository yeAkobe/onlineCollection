package com.zzx.staservice.service;

import com.zzx.staservice.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-21
 */
public interface DailyService extends IService<Daily> {

    //统计某一天注册人数
    void createStatisticsByDay(String day);
    //查询统计数据表的 相关数据
    Map<String, Object> getShowData(String type, String begin, String end);
}
