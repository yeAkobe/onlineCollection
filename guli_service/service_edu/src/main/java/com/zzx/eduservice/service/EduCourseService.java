package com.zzx.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.eduservice.entity.CoursePublishVo;
import com.zzx.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.eduservice.entity.fontVo.CourseFrontVo;
import com.zzx.eduservice.entity.fontVo.CourseWebVo;
import com.zzx.eduservice.entity.vo.CourseInfoForm;
import com.zzx.eduservice.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    //查询课程相关信息
    CourseInfoForm getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoForm courseInfoForm);

    //根据课程id查询课程确认信息
    public
    CoursePublishVo getPublishCourseInfo(String courseId);

    //按条件查询课程信息
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    //根据课程id删除课程
    boolean removeCourse(String id);


    Map<String, Object> getCourseFrontPageList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
