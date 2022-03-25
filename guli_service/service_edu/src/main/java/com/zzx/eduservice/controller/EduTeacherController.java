package com.zzx.eduservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.commonutils.R;
import com.zzx.eduservice.entity.EduTeacher;
import com.zzx.eduservice.entity.vo.TeacherQuery;
import com.zzx.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zzx
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
@Api(description="讲师管理")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;
    @GetMapping("/getAll")
    @ApiOperation(value = "所有讲师列表")
    public
    R fillAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }
    //删除
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据ID删除讲师")
    public R DeleteById(@PathVariable String id){
        val b = eduTeacherService.removeById(id);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }
    //分页查询
    //page：当前页
    //limit：每页显示记录数
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/pageList/{page}/{limit}")
    public R PageList(@ApiParam(name = "page", value = "当前页码", required = true)@PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable Long limit){
        Page<EduTeacher> eduTeacherPage = new Page<>(page,limit);
        eduTeacherService.page(eduTeacherPage,null);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();
        return R.ok().data("items",records).data("total",total);
    }
    //多条件查询讲师带分页
    @ApiOperation(value = "多条件查询讲师带分页")
    @PostMapping("/pageTeacherCondition/{page}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "page", value = "当前页码", required = true)@PathVariable Long page,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> eduTeacherPage = new Page<>(page,limit);
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        //取出值，判断他们是否有值
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            eduTeacherQueryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            eduTeacherQueryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            eduTeacherQueryWrapper.ge("gmt_create",begin);
        }if(!StringUtils.isEmpty(end)){
            eduTeacherQueryWrapper.le("gmt_modified",end);
        }
        eduTeacherQueryWrapper.orderByDesc("gmt_create");

        eduTeacherService.page(eduTeacherPage,eduTeacherQueryWrapper);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return R.ok().data("total",total).data("items",records);
    }
    //新增讲师
    @ApiModelProperty(value = "新增讲师")
    @PostMapping("/save")
    public R SaveTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }
    //根据id查询
    @ApiModelProperty(value = "根据id查询")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("item",byId);
    }
    //修改信息
    @ApiModelProperty(value = "修改讲师")
    @PostMapping("/updateById")
    public R UpdateById(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }
}


