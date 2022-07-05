package com.qzh.articleservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.articleservice.entity.FootVo;
import com.qzh.articleservice.entity.Students;
import com.qzh.articleservice.mapper.StudentsMapper;
import com.qzh.articleservice.service.FootprintService;
import com.qzh.articleservice.utils.ExcelTask;

import com.qzh.articleservice.utils.JwtUtils;
import com.qzh.articleservice.utils.Result;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.zip.ZipOutputStream;

import static com.baomidou.mybatisplus.core.toolkit.SystemClock.nowDate;
import static com.qzh.articleservice.utils.PoiTest.putEntry;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/articleService/footprint")
public class FootprintController {

    @Autowired
    FootprintService footprintService;

    @Resource
    private StudentsMapper studentsMapper;

    @GetMapping("getAll")
    public Result getAll(HttpServletRequest request){
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        List<FootVo> all=new ArrayList<>();
        if(StringUtils.isEmpty(memberId)){
            return Result.ok().data("allFoot",all);
        }
        all = footprintService.getAll();
        return  Result.ok().data("allFoot",all);
    }

    @RequestMapping(value = "/pool/zip", produces = "text/html;charset=UTF-8")
    public void poolZip(HttpServletResponse response,HttpServletRequest request) throws IOException {
        System.out.println("多线程处理ZIP开始...");
        // 当前日期
        String currentDate = nowDate();
        // 创建主线程为4个forkJoin线程池
        ForkJoinPool pool = new ForkJoinPool(16);
        // 字节数组输出流，用于返回压缩后的输出流字节数组
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        // ZIP压缩输出流
        ZipOutputStream zip = new ZipOutputStream(byteOut);
        // Servlet输出流
        OutputStream servletOut=response.getOutputStream();
        System.out.println("开始时间:"+new Date());
        try {
            Page<Students> page = new Page<>(1, 1);
            QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
            IPage<Students> iPage = studentsMapper.selectPage(page, queryWrapper);
            //总数量
            long total = iPage.getTotal();
            // 需要处理的数据任务数量
            List<String> list = new ArrayList<>();
            if(total<1000000){
                list.add("1");
            }else{
                int a=(int)total/1000000;
                for(int i=1;i<=a;i++){
                    list.add(i+"");
                }
                list.add(a+1+"");
            }
            // 通过ForkJoin线程池请求处理
            ForkJoinTask<List<SXSSFWorkbook>> mainTask = new ExcelTask(list);
            // 获取处理结果数据
            List<SXSSFWorkbook> wbList = pool.invoke(mainTask);
            // 文件序号
            int i = 1;
            for (SXSSFWorkbook wb : wbList) {
                // 获取sheet名称
                String sheetName = wb.getSheetName(0);
                // zip条目名称[Excel文件名称]
                String entryName = String.format("%s/%d-%s.xlsx", currentDate, i, sheetName);
                // Excel写入ZIP压缩输出流
                putEntry(entryName, zip, wb);
                i++;
            }
            // 关闭流
            IOUtils.closeQuietly(zip);
            System.out.println("excel文件导出结束时间："+new Date());
            // 注：转换byte[]前必须关闭zip流
            byte[] data = byteOut.toByteArray();
            System.out.println("压缩文件结束时间："+new Date());
            // 设置导出文件名称
            String fileName = String.format("test-%s", currentDate);
            // 设置文件名称编码格式
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            //清除缓冲区数据
            response.reset();
            // 设置下载方式，文件名称
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");
            // 内容的内容类型及编码格式：application/octet-stream[二进制流]
            response.setContentType("application/octet-stream; charset=UTF-8");

            // 把ZIP字节数组写入到servletOut输出流
            IOUtils.write(data, servletOut);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            pool.shutdown();
            // 关闭流
            IOUtils.closeQuietly(byteOut);
            IOUtils.closeQuietly(servletOut);
        }
        System.out.println("结束时间:"+new Date());
    }
}

