package com.zzx.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-05
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontPageList(Page<EduTeacher> teacherPage);
}
