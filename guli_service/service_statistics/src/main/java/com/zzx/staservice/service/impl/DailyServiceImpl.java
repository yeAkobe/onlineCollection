package com.zzx.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.commonutils.R;
import com.zzx.staservice.client.UcenterClient;
import com.zzx.staservice.entity.Daily;
import com.zzx.staservice.mapper.DailyMapper;
import com.zzx.staservice.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-03-21
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    UcenterClient ucenterClient;
    //统计某一天注册人数
    @Override
    public
    void createStatisticsByDay(String day) {
        //添加之前先删除表相同的数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //远程调用得到某一天的注册人数
        R msg = ucenterClient.countRegister(day);
        Integer countRegister =(Integer) msg.getData().get("countRegister");

        //把获取到的数据
        Daily daily = new Daily();
        daily.setRegisterNum(countRegister);//注册人数
        daily.setCourseNum(RandomUtils.nextInt(100,200));
        daily.setLoginNum(RandomUtils.nextInt(200,300));//登录数
        daily.setVideoViewNum(RandomUtils.nextInt(200,300));//视频流量数
        daily.setDateCalculated(day);//统计日期
        //添加到数据库中
        baseMapper.insert(daily);
    }

    //查询统计数据表的 相关数据
    @Override
    public
    Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应的数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated",type);
        wrapper.between("date_calculated",begin,end);

        List<Daily> dailyList = baseMapper.selectList(wrapper);

        //前端要求数组json结果，对应后端为List集合
        //创建两个list集合，一个放日期X轴，一个放数量Y轴
        List<String> xlist = new ArrayList<>();
        List<Integer> ylist = new ArrayList<>();

        for (Daily daily : dailyList) {
            xlist.add(daily.getDateCalculated());

            //判断查询的哪个字段
            if ("register_num".equals(type)){
                ylist.add(daily.getRegisterNum());
            }
            if ("login_num".equals(type)){
                ylist.add(daily.getLoginNum());
            }
            if ("video_view_num".equals(type)){
                ylist.add(daily.getVideoViewNum());
            }
            if ("course_num".equals(type)){
                ylist.add(daily.getCourseNum());
            }

        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("xlist",xlist);
        map.put("ylist",ylist);

        return map;
    }
}
