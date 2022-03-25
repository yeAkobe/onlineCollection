package com.zzx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.eduservice.entity.EduChapter;
import com.zzx.eduservice.entity.EduVideo;
import com.zzx.eduservice.entity.course.ChapterVo;
import com.zzx.eduservice.entity.course.VideoVo;
import com.zzx.eduservice.mapper.EduChapterMapper;
import com.zzx.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.eduservice.service.EduVideoService;
import com.zzx.servicebase.handler.zzxException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;
    //获取课程章节和小节信息
    @Override
    public
    List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //最终要的数据列表
        ArrayList<ChapterVo> finalChapterVos = new ArrayList<>();
        //根据课程id号查询相关的章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);

        //根据课程id查询相关的小节
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(wrapper1);

        //将章节信息赋值给ChapterVo类型的数值
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter chapter = eduChapters.get(i);

            //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            finalChapterVos.add(chapterVo);

            //填充课时vo对象
            //将小节信息赋值给ChapterVo下的children属性
            ArrayList<VideoVo> finalVideoVos = new ArrayList<>();
            for (int j = 0; j < eduVideos.size(); j++) {
                EduVideo video = eduVideos.get(j);

                if (chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    finalVideoVos.add(videoVo);
                }
            }

            chapterVo.setChildren(finalVideoVos);

        }

        return finalChapterVos;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapter章节id 查询查询小节表，如果查询有数据，则不删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);
        if(count>0){
            try {
                throw new zzxException(20001,"该章节有小节信息无法删除！");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }else{
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
        return false;
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }


}
