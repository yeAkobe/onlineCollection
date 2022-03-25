package com.zzx.eduservice.entity.forExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubjectData {

    //一级分类
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    //二级分类
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}


