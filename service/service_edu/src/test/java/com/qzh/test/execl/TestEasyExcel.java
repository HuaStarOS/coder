package com.qzh.test.execl;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qzh
 * @Date: 2021/4/10 16:06
 */
public class TestEasyExcel {

    public static void main(String[] args) {
        // 实现excel写的操作
        // 1.设置写入文件夹地址和excel文件名称
//        String filename = "D:\\write.xlsx";

        // 2. 调用easyExcel里面的方法实现写操作
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getLists());
        // 实现excel读操作
        String filename = "D:\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();

    }

    //创建方法返回List集合
    private static List<DemoData> getLists(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("qzh ："+ i);
            list.add(demoData);
        }
        return list;
    }

}
