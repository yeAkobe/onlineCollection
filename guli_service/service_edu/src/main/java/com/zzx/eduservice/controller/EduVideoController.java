package com.zzx.eduservice.controller;


import com.zzx.commonutils.R;
import com.zzx.eduservice.client.VodClient;
import com.zzx.eduservice.entity.EduVideo;
import com.zzx.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;
    //导入nacos接口
    @Autowired
    VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public
    R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }


    //删除小节
    // TODO 后面这个方法需要完善，删除小节的时候，同时也要把视频删除
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        //根据id查询视频id
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节中是否有对应的视频文件
        if (!StringUtils.isEmpty(videoSourceId)){
            //有就删除
            vodClient.removeAliyunVideoById(videoSourceId);
        }

        //删除小节信息
        eduVideoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    //根据小节id查询
    @GetMapping("/getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }
}

