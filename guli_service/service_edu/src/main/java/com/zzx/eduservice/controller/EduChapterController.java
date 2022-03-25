package com.zzx.eduservice.controller;


import com.zzx.commonutils.R;
import com.zzx.eduservice.entity.EduChapter;
import com.zzx.eduservice.entity.course.ChapterVo;
import com.zzx.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    //获取课程大纲列表，根据课程id进行查询
    @GetMapping("/getChapterVideo/{courseId}")
    public
    R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("allChapterVideo",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //根据章节id查询
    @GetMapping("getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("deleteById/{chapterId}")
    public R deleteById(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }


}

