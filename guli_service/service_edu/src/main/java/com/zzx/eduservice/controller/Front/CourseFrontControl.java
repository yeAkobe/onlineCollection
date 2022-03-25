package com.zzx.eduservice.controller.Front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.commonutils.JwtUtils;
import com.zzx.commonutils.R;
import com.zzx.eduservice.client.OrderClient;
import com.zzx.eduservice.entity.EduCourse;
import com.zzx.eduservice.entity.EduTeacher;
import com.zzx.eduservice.entity.course.ChapterVo;
import com.zzx.eduservice.entity.fontVo.CourseFrontVo;
import com.zzx.eduservice.entity.fontVo.CourseWebVo;
import com.zzx.eduservice.service.EduChapterService;
import com.zzx.eduservice.service.EduCourseService;
import com.zzx.eduservice.service.EduVideoService;
import com.zzx.servicebase.handler.zzxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/eduservice/courseFront")
public
class CourseFrontControl {
    @Autowired
    EduCourseService eduCourseService;
    @Autowired
    EduChapterService eduChapterService;
    @Autowired
    EduVideoService vodService;
    @Autowired
    OrderClient orderClient;

    //条件分页查询课程信息
    @PostMapping("getConditionPage/{page}/{limit}")
    public
    R GetConditionPage(@PathVariable Long page, @PathVariable Long limit,
                        @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(page, limit);

        Map<String,Object> map = eduCourseService.getCourseFrontPageList(coursePage,courseFrontVo);

        //返回分页中的所有数据
        return R.ok().data(map);
    }
    //课程详情的方法
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        boolean isBuyCourse = false;
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);

        //根据课程id，查询章节和小节信息
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);

        //获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        if (!StringUtils.isEmpty(memberId)){
            //根据课程id、用户id，查询课程是否已经购买
            isBuyCourse = orderClient.isBuyCourse(memberId, courseId);
        }

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",isBuyCourse);

    }




}
