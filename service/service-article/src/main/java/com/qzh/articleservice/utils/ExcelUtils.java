package com.qzh.articleservice.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.articleservice.entity.Students;
import com.qzh.articleservice.mapper.StudentsMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Excel工具类
 *
 * @author tangyb
 * @date 2020/04/21
 */
@Component
public class ExcelUtils {

    @Resource
    private StudentsMapper studentsMapper;
    private static  StudentsMapper su;
    @PostConstruct
    public void init(){
        su=studentsMapper;
    }

    /**
     * 创建Excel工作薄并写入数据
     */
    public static SXSSFWorkbook createExcel(String sheetName) throws InterruptedException {
        // 1.声明一个工作薄
        SXSSFWorkbook  workbook = new SXSSFWorkbook(1000);
        // 2.创建新工作表
        Sheet sheet = workbook.createSheet("标识数据"+sheetName);

        /** 4.这里是你去创建表头以及对应数据的方法 dealData(); start */
        // 4.1.设置首行标题
        Row row = sheet.createRow((short) 0);
        // 声明Excel标题名称
        String[] excelHeader = {"id", "用户名", "年龄", "描述"};
        // 创建标题首行列
        for (int i = 0; i < excelHeader.length; i++) {
            Cell cell = row.createCell(i); // 列下标
            cell.setCellValue(excelHeader[i]); // 列值
        }
        // 4.2.多线程填充当前数据行列，从下标1跳过标题行开始
        int i = Integer.parseInt(sheetName);
        int start = (i-1)*10+1;
        int end = i*10;
        for(;start<=end;start++){
            Page<Students> page = new Page<>(start, 100000);
            IPage<Students> iPage = su.selectPage(page, null);
            List<Students> records = iPage.getRecords();
            for (int j = 0; j <records.size(); j++) {
                int x=start%10==0?10:start%10;
                int rowNumber = j+(x-1)*100000;
                row = sheet.createRow(rowNumber);
                Cell cell;
                Students students = records.get(j);
                for (int k = 0; k < 4; k++) {
                    cell = row.createCell(k);
                    cell.setCellValue(students.getName());
                }
            }
        }
        return workbook;
    }

}

