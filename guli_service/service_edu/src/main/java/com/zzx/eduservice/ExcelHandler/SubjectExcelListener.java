package com.zzx.eduservice.ExcelHandler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.eduservice.entity.EduSubject;
import com.zzx.eduservice.entity.forExcel.SubjectData;
import com.zzx.eduservice.service.EduSubjectService;
import com.zzx.servicebase.handler.zzxException;

public
class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener態交给spring进行ioc管理，需要自己手动new，不能注入其他对象
    //不能实现数据库操作
    public SubjectExcelListener(){}
    public EduSubjectService eduSubjectService;
    //有参，传递subjectService用于操作数据库
    public
    SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public
    void invoke(SubjectData data, AnalysisContext context) {
        //data代表从Exel中读到的数据
        //先判断excel中是否有值
        if(data==null){
            try {
                throw new zzxException(20001,"表中数据为空！");
            } catch (zzxException e) {
                e.printStackTrace();
            }
        }

        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断是否有一级分类是否重复
        EduSubject existOneSubject  = this.existOneSubject(eduSubjectService, data.getOneSubjectName());
        if(existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(data.getOneSubjectName());
            existOneSubject.setParentId("0");
            eduSubjectService.save(existOneSubject);
        }

        String pid = existOneSubject.getId();
        //判断是否有二级分类重复
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, data.getTwoSubjectName(), pid);
        if(existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(data.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            eduSubjectService.save(existTwoSubject);
        }
    }
    //判断一级目录是否已经存在
    private
    EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("title",name);
        eduSubjectQueryWrapper.eq("parent_id",0);
        EduSubject one = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return one;
    }
    //判断二级目录是否已经存在
    private
    EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String parentId){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("title",name);
        eduSubjectQueryWrapper.eq("parent_id",parentId);
        EduSubject two = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return two;
    }

    @Override
    public
    void doAfterAllAnalysed(AnalysisContext context) {

    }
}
