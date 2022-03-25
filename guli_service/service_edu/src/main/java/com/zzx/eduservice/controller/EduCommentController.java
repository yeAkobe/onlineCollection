package com.zzx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.commonutils.JwtUtils;
import com.zzx.commonutils.R;
import com.zzx.commonutils.vo.UcenterMemberVo;
import com.zzx.eduservice.client.UcenterClient;
import com.zzx.eduservice.entity.EduComment;
import com.zzx.eduservice.service.EduCommentService;
import com.zzx.servicebase.handler.zzxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/eduservice/edu-comment")
//@CrossOrigin
public class EduCommentController {

    @Autowired
    EduCommentService eduCommentService;
    @Autowired
    UcenterClient ucenterClient;
    //根据课程id_分页查询课程评论的方法
    @GetMapping("/getCommentPage/{page}/{limit}")
    public
    R getCommentPage(@PathVariable Long page, @PathVariable Long limit, String courseId){
        Page<EduComment> commentPage = new Page<>(page, limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();

        //判断课程id是否为空
        if (!StringUtils.isEmpty(courseId)){
            wrapper.eq("course_id",courseId);
        }

        //按最新排序
        wrapper.orderByDesc("gmt_create");

        //数据会被封装到commentPage中
        eduCommentService.page(commentPage,wrapper);

        List<EduComment> commentList = commentPage.getRecords();
        long current = commentPage.getCurrent();//当前页
        long size = commentPage.getSize();//一页记录数
        long total = commentPage.getTotal();//总记录数
        long pages = commentPage.getPages();//总页数
        boolean hasPrevious = commentPage.hasPrevious();//是否有上页
        boolean hasNext = commentPage.hasNext();//是否有下页

        HashMap<String, Object> map = new HashMap<>();
        map.put("current",current);
        map.put("size",size);
        map.put("total",total);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        map.put("list",commentList);

        return R.ok().data(map);
    }

    //添加评论
    @PostMapping("/auth/addComment")
    public R addComment(HttpServletRequest request,@RequestBody EduComment eduComment){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //判断用户是否登录
        if (StringUtils.isEmpty(memberId)){
            try {
                throw new zzxException(20001,"请先登录");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }
        eduComment.setMemberId(memberId);

        //远程调用ucenter根据用户id获取用户信息
        UcenterMemberVo memberVo = ucenterClient.getMemberInfoById(memberId);

        eduComment.setAvatar(memberVo.getAvatar());
        eduComment.setNickname(memberVo.getNickname());

        //保存评论
        eduCommentService.save(eduComment);

        return R.ok();
    }



}

