package com.zzx.eduservice.service;

import com.zzx.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程id删除小节
    void removeVideoByCourseId(String id);

}
