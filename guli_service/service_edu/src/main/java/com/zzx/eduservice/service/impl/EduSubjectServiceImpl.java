package com.zzx.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.eduservice.ExcelHandler.SubjectExcelListener;
import com.zzx.eduservice.entity.EduSubject;
import com.zzx.eduservice.entity.forExcel.SubjectData;
import com.zzx.eduservice.entity.subject.OneSubject;
import com.zzx.eduservice.entity.subject.TwoSubject;
import com.zzx.eduservice.mapper.EduSubjectMapper;
import com.zzx.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.servicebase.handler.zzxException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-03-11
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public
    void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try{

            //获取输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
            try {
                throw new zzxException(20002,"添加课程失败");
            } catch (com.zzx.servicebase.handler.zzxException zzxException) {
                zzxException.printStackTrace();
            }
        }
    }

    //获取指定数据类型的课程分类数据
    @Override
    public
    List<OneSubject> getAllOneTwoSubject() {
        //查询一级分类的数据
        QueryWrapper<EduSubject> oneQueryWrapper = new QueryWrapper<>();
        oneQueryWrapper.eq("parent_id","0");
        List<EduSubject> eduOneSubjects = baseMapper.selectList(oneQueryWrapper);//用ServiceImpl继承来的baseMapper查询出list类型的数据
//查询二级分类的数据
        QueryWrapper<EduSubject> twoQueryWrapper = new QueryWrapper<>();
        twoQueryWrapper.ne("parent_id","0");
        List<EduSubject> eduTwoSubjects = baseMapper.selectList(twoQueryWrapper);
        //将要返回的指定类型的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //将一级分类的数据导入到OneSubject中
        for(int i=0; i<eduOneSubjects.size(); i++){
            EduSubject oneEdu = eduOneSubjects.get(i);
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());
            //把eduSubject值复制到oneSubject中去【要求两个类的复制注入的属性名一致】
            BeanUtils.copyProperties(oneEdu,oneSubject);
            //将获取到的oneSubject类型的数据添加到list数组中
            finalSubjectList.add(oneSubject);

            //返回二级分类的数据
            List<TwoSubject> finalTwoSubjectList = new ArrayList<>();
            //将二级分类的数据导入
            for(int j=0; j<eduTwoSubjects.size(); j++){
                //获取每个二级分类
                EduSubject twoEdu = eduTwoSubjects.get(j);
                if(oneEdu.getId().equals(twoEdu.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEdu,twoSubject);
                    finalTwoSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有二级分类放到oneSubject里面
            oneSubject.setChildren(finalTwoSubjectList);
        }



        return finalSubjectList;
    }
}
