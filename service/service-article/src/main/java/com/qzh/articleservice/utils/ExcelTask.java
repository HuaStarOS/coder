package com.qzh.articleservice.utils;

import lombok.SneakyThrows;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @ClassName ExcelTask
 * @Description: TODO
 * @Author admin
 * @Date 18/6/2021
 **/
public class ExcelTask extends RecursiveTask<List<SXSSFWorkbook>> {


    /**
     * 每个线程阈值
     */
    private static final int THRESHOLD = 10;

    /**
     * 处理的数据集合
     */
    private final List<String> dataList;

    /**
     * 初始化类并赋值
     *
     * @param dataList
     */
    public ExcelTask(List<String> dataList) {
        this.dataList = dataList;
    }

    /**
     * @see java.util.concurrent.RecursiveTask#compute()
     */
    @SneakyThrows
    @Override
    protected List<SXSSFWorkbook> compute() {
        // 数据大小
        int size = dataList.size();
        System.out.println("压缩子线程处理数据大小："+size);

        if (size <= THRESHOLD) {
            return process(); // 大小等于阈值顺序计算结果
        }
        // 第一个任务分组
        ExcelTask ltTask = new ExcelTask(dataList.subList(0, size / 2));
        // 第二个任务分组
        ExcelTask rtTask = new ExcelTask(dataList.subList(size / 2, size));
        // 两个任务并发执行起来
        invokeAll(ltTask, rtTask);
        List<SXSSFWorkbook> allList = new ArrayList<SXSSFWorkbook>();
        // 读取第一个任务分组处理的结果
        List<SXSSFWorkbook> ltResult = ltTask.join();
        // 读取第二个任务分组处理的结果
        List<SXSSFWorkbook> rtResult = rtTask.join();
        // 合并结果集合
        allList.addAll(ltResult);
        allList.addAll(rtResult);

        return allList;
    }

    /**
     * 业务实现方法
     *
     * @return
     */
    private List<SXSSFWorkbook> process() throws InterruptedException {
        List<SXSSFWorkbook> wbList = new ArrayList<SXSSFWorkbook>();
        for (String sheetName : dataList) {
            SXSSFWorkbook workbook = ExcelUtils.createExcel(sheetName);
            wbList.add(workbook);
        }
        return wbList;
    }

}
