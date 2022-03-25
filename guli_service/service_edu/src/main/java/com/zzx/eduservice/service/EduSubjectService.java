package com.zzx.eduservice.service;

import com.zzx.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void addSubject(MultipartFile file, EduSubjectService eduSubjectService);

    //获取指定数据类型的课程分类数据
    List<OneSubject> getAllOneTwoSubject();
}
