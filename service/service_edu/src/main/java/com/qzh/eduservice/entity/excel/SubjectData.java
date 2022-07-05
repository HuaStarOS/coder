package com.qzh.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: qzh
 * @Date: 2021/4/10 17:23
 */
@Data
@ToString
public class SubjectData {

    /**
     * 一级分类
     */
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    /**
     * 二级分类
     */
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}

