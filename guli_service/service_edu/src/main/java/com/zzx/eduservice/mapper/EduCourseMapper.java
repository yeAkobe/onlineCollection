package com.zzx.eduservice.mapper;

import com.zzx.eduservice.entity.CoursePublishVo;
import com.zzx.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzx.eduservice.entity.fontVo.CourseWebVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
@Component
public interface EduCourseMapper extends BaseMapper<EduCourse> {
        public
        CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
