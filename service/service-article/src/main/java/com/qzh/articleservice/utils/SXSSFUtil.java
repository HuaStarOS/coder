package com.qzh.articleservice.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 导出大数据量的excel，支持百万级数据量导出
 */
public class SXSSFUtil {

	/**
	 * 导出excel
	 *
	 * @param response HttpServletResponse
	 * @param list     导出数据集合
	 * @param fields   key数组
	 * @param fileName 文件名
	 */
	public static void excelExport(SXSSFWorkbook sxssfWorkbook,HttpServletResponse response, List<Map<String, Object>> list, String[] fields, String fileName) {
		String filename = "";
		try {
			// 设置文件名称，并处理中文乱码
			filename = new String(fileName.getBytes("UTF-8"), "ISO_8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("application/octet-stream");//告诉浏览器输出内容为流
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");// 不同类型的文件对应不同的MIME类型
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);

		OutputStream os = null;
		try {
			// 创建SXSSFWorkbook，100条数据在内存中，其它的数据都会写到磁盘里
			sxssfWorkbook = new SXSSFWorkbook(100);
			Sheet sheet = sxssfWorkbook.createSheet(fileName);
			// 创建第一行,作为header表头
			Row header = sheet.createRow(0);
			// 遍历创建行,导出数据
			for (int rownum = 1; rownum <= list.size(); rownum++) {
				Row row = sheet.createRow(rownum);
				Map<String, Object> map = list.get(rownum - 1);
				// 循环创建单元格
				for (int cellnum = 0; cellnum < fields.length; cellnum++) {
					Cell cell = row.createCell(cellnum);
					//自定义设置数据行单元格样式,根据需要设置
					//cell.setCellStyle(getAndSetXSSFCellStyleOne(sxssfWorkbook));
					cell.setCellValue(map.get(fields[cellnum]) == null ? "" : map.get(fields[cellnum]).toString());
				}
			}
			//自定义各列宽度
			//setSheet(sheet);
			os = response.getOutputStream();
			sxssfWorkbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置excel表头
	 *
	 * @param labels   表头数组
	 */
	public static void setExcelHeader(String[] labels,Row header){
		// 循环创建header单元格
		for (int cellnum = 0; cellnum < labels.length; cellnum++) {
			Cell cell = header.createCell(cellnum);
			//cell.setCellStyle(getAndSetXSSFCellStyleHeader(sxssfWorkbook));//设置表头单元格样式,根据需要设置
			cell.setCellValue(labels[cellnum]);
			//自定义设置每列固定宽度
			//sheet.setColumnWidth(cellnum, 10 * 256);
		}
	}

}
