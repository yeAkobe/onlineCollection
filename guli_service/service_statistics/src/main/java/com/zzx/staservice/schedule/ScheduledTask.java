package com.zzx.staservice.schedule;

import com.zzx.staservice.service.DailyService;
import com.zzx.staservice.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private DailyService dailyService;

    //（0/5 * * * * ？）：每隔5秒执行一次
    @Scheduled(cron = "0/5 * * * * ?")//指定cron表达式规则
    public void task01(){
        System.out.println("=========我执行了");
    }

    //在每天凌晨1点执行方法，把前一天的数据查询进行添加
    @Scheduled(cron = "0 0 1 * * ? ")//指定cron表达式规则
    public void task02(){
        dailyService.createStatisticsByDay(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}

