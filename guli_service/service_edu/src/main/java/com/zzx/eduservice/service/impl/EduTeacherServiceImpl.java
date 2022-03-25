package com.zzx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.eduservice.entity.EduTeacher;
import com.zzx.eduservice.mapper.EduTeacherMapper;
import com.zzx.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-03-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public
    Map<String, Object> getTeacherFrontPageList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象中
        baseMapper.selectPage(teacherPage,wrapper);

        //把分页的数据获取出来返回一个map集合
        HashMap<String, Object> map = new HashMap<>();

        //总记录数
        long total = teacherPage.getTotal();
        //当前页
        long current = teacherPage.getCurrent();
        //每页记录数
        long size = teacherPage.getSize();
        //查询到的对象
        List<EduTeacher> teacherList = teacherPage.getRecords();
        //总页数
        long pages = teacherPage.getPages();
        //是否有上一页
        boolean hasPrevious = teacherPage.hasPrevious();
        //是否有下一页
        boolean hasNext = teacherPage.hasNext();

        //将数据封装到map中返回
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("list",teacherList);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        map.put("pages",pages);

        return map;
    }
}
