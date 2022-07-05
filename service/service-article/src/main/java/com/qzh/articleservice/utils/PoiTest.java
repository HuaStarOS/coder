package com.qzh.articleservice.utils;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName PoiTest
 * @Description: TODO
 * @Author admin
 * @Date 18/6/2021
 **/
public class PoiTest {

    /**
     * 使用多线程进行Excel写操作，提高写入效率。
     */
    public static void multiThreadWrite(String[] labels,String fileName) {
        /**,
         * 使用线程池进行线程管理。
         */
        ExecutorService es = Executors.newCachedThreadPool();
        /**
         * 使用计数栅栏
         */
        CountDownLatch doneSignal = new CountDownLatch(1);

        SXSSFWorkbook sxssfWorkbook;
        try {
            sxssfWorkbook = new SXSSFWorkbook(100);
            Sheet sheet = sxssfWorkbook.createSheet(fileName);
            Row header = sheet.createRow(0);
            //设置第一行头
            setExcelHeader(labels,header);
            es.submit(new PoiWriter(doneSignal, sheet, 1, 1000000));
            /**
             * 使用CountDownLatch的await方法，等待所有线程完成sheet操作
             */
            doneSignal.await();
            es.shutdown();
            FileOutputStream os = new FileOutputStream("D:\\data\\poiTest.xls");
            sxssfWorkbook.write(os);
            os.flush();
            os.close();
            System.out.println("Excel completed......");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置excel表头
     *
     * @param labels   表头数组
     */
    public static void setExcelHeader(String[] labels, Row header){
        // 循环创建header单元格
        for (int cellnum = 0; cellnum < labels.length; cellnum++) {
            Cell cell = header.createCell(cellnum);
            //cell.setCellStyle(getAndSetXSSFCellStyleHeader(sxssfWorkbook));//设置表头单元格样式,根据需要设置
            cell.setCellValue(labels[cellnum]);
            //自定义设置每列固定宽度
            //sheet.setColumnWidth(cellnum, 10 * 256);
        }
    }

    /**
     * 进行sheet写操作的sheet。
     * @author alex
     *
     */
    protected static class PoiWriter implements Runnable {

        private final CountDownLatch doneSignal;

        private Sheet sheet;

        private int start;

        private int end;


        public PoiWriter(CountDownLatch doneSignal, Sheet sheet, int start,
                         int end) {
            this.doneSignal = doneSignal;
            this.sheet = sheet;
            this.start = start;
            this.end = end;
        }
        @Override
        public void run() {
            int i = start;
            try {
                // 遍历创建行,导出数据
                for (; i <= end; ++i) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
                System.out.println("start: " + start + " end: " + end
                        + " Count: " + doneSignal.getCount());
            }
        }

    }

    /**
     * sheet的row使用treeMap存储的，是非线程安全的，所以在创建row时需要进行同步操作。
     * @param sheet
     * @param rownum
     * @return
     */
    private static synchronized Row getRow(Sheet sheet, int rownum) {
        return sheet.createRow(rownum);
    }

    /**
     * Excel写入zip压缩输出流
     * @param entryName
     * @param zip
     * @param workbook
     * @throws IOException
     */
    public static void putEntry(String entryName, ZipOutputStream zip,
                          SXSSFWorkbook workbook) throws IOException {
        // 写入ZIP文件条目并指定名称
        zip.putNextEntry(new ZipEntry(entryName));
        // 字节数组输出流，用于转存Excel工作薄字节
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Excel工作薄写入字节数组输出流
        workbook.write(bos);
        // 字节数组输出流的完整内容写入zip
        bos.writeTo(zip);
        // 刷新压缩输出流
        zip.flush();
        // 关闭当前的ZIP条目并定位流以写入下一个条目
        zip.closeEntry();
        IOUtils.closeQuietly(bos);
    }

    /**
     * 获取当前日期，格式yyyyMMdd
     *
     * @return
     */
    private static String nowDate() {
        // 添加当前时间
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(now);
    }

    public static void main(String[] args) throws FileNotFoundException {

    }
}
