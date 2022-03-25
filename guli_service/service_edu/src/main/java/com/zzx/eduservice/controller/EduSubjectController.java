package com.zzx.eduservice.controller;


import com.zzx.commonutils.R;
import com.zzx.eduservice.entity.subject.OneSubject;
import com.zzx.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
//@CrossOrigin
@Api(description="课程分类管理")
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读取出来
    @PostMapping("/saveSubject")
    public
    R saveSubject(MultipartFile file){
        //获取上传的excel文件 MultipartFile
        eduSubjectService.addSubject(file,eduSubjectService);
        return R.ok();
    }

    //返回指定格式的课程分类的数据
    @GetMapping("/getAllSubject")
    public R getSubject(){
        List<OneSubject> data = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",data);
    }
}

