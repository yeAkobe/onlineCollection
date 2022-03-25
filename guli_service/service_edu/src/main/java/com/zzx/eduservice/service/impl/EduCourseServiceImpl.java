package com.zzx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.eduservice.entity.CoursePublishVo;
import com.zzx.eduservice.entity.EduCourse;
import com.zzx.eduservice.entity.EduCourseDescription;
import com.zzx.eduservice.entity.fontVo.CourseFrontVo;
import com.zzx.eduservice.entity.fontVo.CourseWebVo;
import com.zzx.eduservice.entity.vo.CourseInfoForm;
import com.zzx.eduservice.entity.vo.CourseQuery;
import com.zzx.eduservice.mapper.EduCourseMapper;
import com.zzx.eduservice.service.EduChapterService;
import com.zzx.eduservice.service.EduCourseDescriptionService;
import com.zzx.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.eduservice.service.EduVideoService;
import com.zzx.servicebase.handler.zzxException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService courseDescriptionService;
    @Autowired
    EduCourseMapper eduCourseMapper;
    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    EduChapterService eduChapterService;
    //添加课程
    @Override
    public
    String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //添加数据到edu_course表中
        EduCourse eduCourse = new EduCourse();
        //将传过来的courseInfoForm类型数据，保存成eduCourse类型数据
        //这样才能在这个类中用baseMapper添加数据
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            try {
                throw new zzxException(20001,"添加课程失败！");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }
        //添加数据到edu_course_description表中
        String cid = eduCourse.getId();//获取添加到edu_course表中的id信息，保证edu_course表和edu_course_description表一对一的关系
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //设置添加的信息
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return eduCourse.getId();
    }

    //根据课程id查询课程基本信息
    @Override
    public
    CourseInfoForm getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        //查询简介表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoForm.setDescription(courseDescription.getDescription());
        return courseInfoForm;
    }

    //修改课程信息
    @Override
    public
    void updateCourseInfo(CourseInfoForm courseInfoForm) {

        //1、修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update <= 0){
            try {
                throw new zzxException(20001,"修改课程信息失败");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }

        //2、修改描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(courseInfoForm.getId());
        courseDescriptionService.updateById(eduCourseDescription);
    }

    //根据课程id查询课程确认信息
    @Override
    public
    CoursePublishVo getPublishCourseInfo(String courseId) {
        return eduCourseMapper.getPublishCourseInfo(courseId);
    }

    //按查询条件查询课程信息
    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {

        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //取出值，判断他们是否有值
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        //判断条件值是否为空，如果不为空，拼接条件
        //判断是否有传入教师名
        if (!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.like("title", title);//参数1：数据库字段名； 参数2：模糊查询的值
        }
        //判断是否传入状态
        if (!StringUtils.isEmpty(status)) {
            //构造条件
            wrapper.eq("status", status);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //带上门判断后的条件进行分页查询
        baseMapper.selectPage(pageParam, wrapper);
    }

    //删除课程
    @Override
    public boolean removeCourse(String id) {
        //1、根据课程id删除小节
        eduVideoService.removeVideoByCourseId(id);

        //2、根据课程id删除章节部分
        eduChapterService.removeChapterByCourseId(id);

        //3、根据课程id删除课程描述
        courseDescriptionService.removeById(id);

        //4、根据课程id删除课程本身
        int result = baseMapper.deleteById(id);

        if (result>=1){
            return true;
        }else {
            try {
                throw new zzxException(20001,"删除失败");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    //条件分页查询课程信息
    @Override
    public
    Map<String, Object> getCourseFrontPageList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {

            String title = null;
            String subjectId = null;
            String subjectParentId = null;
            String gmtCreateSort = null;
            String buyCountSort = null;
            String priceSort = null;
            String teacherId = null;

            if (!StringUtils.isEmpty(courseFrontVo)){
                title = courseFrontVo.getTitle();
                subjectId = courseFrontVo.getSubjectId();
                subjectParentId = courseFrontVo.getSubjectParentId();
                gmtCreateSort = courseFrontVo.getGmtCreateSort();
                buyCountSort = courseFrontVo.getBuyCountSort();
                priceSort = courseFrontVo.getPriceSort();
                teacherId = courseFrontVo.getTeacherId();
            }


            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            //判断条件值是否为空，不为空拼接条件
            if (!StringUtils.isEmpty(subjectParentId)){//一级分类
                wrapper.eq("subject_parent_id",subjectParentId);
            }
            if (!StringUtils.isEmpty(subjectId)){//二级分类
                wrapper.eq("subject_id",subjectId);
            }
            if (!StringUtils.isEmpty(buyCountSort)){//关注度
                wrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isEmpty(priceSort)){//价格
                wrapper.orderByDesc("price");
            }
            if (!StringUtils.isEmpty(gmtCreateSort)){//最新，创建时间
                wrapper.orderByDesc("gmt_create");
            }


            baseMapper.selectPage(coursePage, wrapper);

            long total = coursePage.getTotal();//总记录数
            List<EduCourse> courseList = coursePage.getRecords();//数据集合
            long size = coursePage.getSize();//每页记录数
            long current = coursePage.getCurrent();//当前页
            long pages = coursePage.getPages();//总页数
            boolean hasPrevious = coursePage.hasPrevious();//是否有上一页
            boolean hasNext = coursePage.hasNext();//是否有下一页

            HashMap<String, Object> map = new HashMap<>();
            map.put("total",total);
            map.put("list",courseList);
            map.put("size",size);
            map.put("current",current);
            map.put("pages",pages);
            map.put("hasPrevious",hasPrevious);
            map.put("hasNext",hasNext);

            return map;
    }

    //查询课程详细信息
    @Override
    public
    CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }


}
