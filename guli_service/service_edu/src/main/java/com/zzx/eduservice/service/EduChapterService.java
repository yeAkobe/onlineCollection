package com.zzx.eduservice.service;

import com.zzx.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.eduservice.entity.course.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
public interface EduChapterService extends IService<EduChapter> {

    //获取课程章节和小节信息
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节
    boolean deleteChapter(String chapterId);

    //根据课程id删除章节
    void removeChapterByCourseId(String id);
}
