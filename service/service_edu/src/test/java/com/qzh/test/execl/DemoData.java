package com.qzh.test.execl;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: qzh
 * @Date: 2021/4/10 16:03
 */
@Data
public class DemoData {
    // 设置excel表头名称
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
