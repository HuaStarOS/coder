package com.qzh.articleservice.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.articleservice.entity.Students;
import com.qzh.articleservice.mapper.StudentsMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author Shinelon
 * @Date 2021/6/20 17:05
 * @Version 1.0
 */

public class ExcelWriter implements Runnable{

    private  CountDownLatch doneSignal;

    private StudentsMapper studentsMapper;

    private Sheet sheet;


    private int start;

    private int end;


    public ExcelWriter(CountDownLatch doneSignal,Sheet sheet, int start,
                     int end,StudentsMapper studentsMapper) {
        this.doneSignal=doneSignal;
        this.sheet = sheet;
        this.start = start;
        this.end = end;
        this.studentsMapper=studentsMapper;
    }
    @Override
    public void run() {
        try {
            Page<Students> page = new Page<>(start, 100000);
            IPage<Students> iPage = studentsMapper.selectPage(page, null);
            List<Students> records = iPage.getRecords();
            for (int j = 0; j <records.size(); j++) {
                int x=start%10==0?10:start%10;
                int rowNumber = j+(x-1)*100000;
                Row row = getRow(sheet,rowNumber);
                Cell cell;
                Students students = records.get(j);
                for (int k = 0; k < 4; k++) {
                    cell = row.createCell(k);
                    writeCell(cell,students.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            doneSignal.countDown();
            System.out.println("start: " + start + " end: " + end
                    + " Count: " + doneSignal.getCount());
        }
    }

    /**
     * sheet的row使用treeMap存储的，是非线程安全的，所以在创建row时需要进行同步操作。
     * @param sheet
     * @param rownum
     * @return
     */
    private static  Row getRow(Sheet sheet, int rownum) {
        synchronized(Object.class){
            return sheet.createRow(rownum);
        }
    }

    public static synchronized void writeCell(Cell cell,String text){
        cell.setCellValue(text);
    }
}
