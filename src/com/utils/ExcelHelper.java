package com.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mvc.entityReport.CheckHouse;
/**
 * Excel操作类
 * 
 * @author wangrui
 * @date 2016-11-15
 */
@SuppressWarnings("hiding")
public class ExcelHelper<T> {

	/**
	 * 导出2007版单sheet的Excel
	 * 
	 * @param title
	 *            表格标题
	 * @param headers
	 *            Excel表头
	 * @param list
	 *            Excel单元格内容
	 * @param out
	 *            输出流
	 * @param pattern
	 * @param mergeColumn
	 *            合并单元格列数，-1表示没有合并单元格,从0开始
	 * @param hideColumn
	 *            隐藏表格内容的最后几列，0表示没有隐藏
	 * @param titleRow
	 *            表头行数，null或0代表没有表头
	 */
	public void export2007Excel(String title, String[] headers, Collection<T> list, OutputStream out, String pattern,
			Integer mergeColumn, Integer MergeColumn, Integer MergeColumn0, Integer hideColumn, Integer titleRow) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		export2007Excel(workbook, title, headers, list, pattern, mergeColumn, MergeColumn, MergeColumn0, hideColumn,
				titleRow);
		write2007Out(workbook, out);
	}

	/**
	 * 导出2007版单sheet的Excel
	 * 
	 * @param title
	 *            表格标题
	 * @param headers
	 *            Excel表头
	 * @param list
	 *            Excel单元格内容
	 * @param out
	 *            输出流
	 * @param pattern
	 * @param mergeColumn
	 *            合并单元格列数，-1表示没有合并单元格,从0开始
	 * @param hideColumn
	 *            隐藏表格内容的最后几列，0表示没有隐藏
	 * @param titleRow
	 *            表头行数，null或0代表没有表头
	 */
	public void export2007Excel(String title, String[] headers, Collection<T> list, OutputStream out, String pattern,
			Integer mergeColumn, Integer MergeColumn, Integer MergeColumn0, Integer hideColumn, Integer titleRow,
			 Integer isList) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		export2007Excel(workbook, title, headers, list, pattern, mergeColumn, MergeColumn, MergeColumn0, hideColumn,
				titleRow, isList);
		write2007Out(workbook, out);
	}
	/**
	 * 导出2007版多sheet的Excel
	 * 
	 * @param titlesMap
	 * @param headerMap
	 * @param map
	 * @param out
	 * @param pattern
	 * @param mergeColumn
	 * @param hideColumn
	 * @param titleRow
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void export2007MutiExcel(Map<Integer, String> titlesMap, Map<Integer, String[]> headerMap,
			Map<Integer, List> map, OutputStream out, String pattern, Integer mergeColumn, Integer MergeColumn,
			Integer MergeColumn0, Integer hideColumn, Integer titleRow) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		for (int i = 0; i < map.size(); i++) {
			Collection<T> list = map.get(i);
			String[] headers = headerMap.get(i);
			String title = titlesMap.get(i);
			export2007Excel(workbook, title, headers, list, pattern, mergeColumn, MergeColumn, MergeColumn0, hideColumn,
					titleRow);
		}
		write2007Out(workbook, out);
	}

	/**
	 * 导出2007版Excel
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param list
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的:
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 * @param mergeColumn
	 *            合并单元格列数，-1表示没有合并单元格
	 * @param hideColumn
	 *            隐藏表格内容的最后几列，0表示没有隐藏
	 * @param titleRow
	 *            表头行数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void export2007Excel(XSSFWorkbook workbook, String title, String[] headerSource, Collection<T> list,
			String pattern, Integer mergeColumn, Integer MergeColumn, Integer MergeColumn0, Integer hideColumn,
			Integer titleRow) {
		XSSFSheet sheet = workbook.createSheet(title);
		if (titleRow == null) {
			titleRow = 0;
		}
		dealHeader(headerSource, titleRow, workbook, sheet, title);
		XSSFRow row = null;
		XSSFCellStyle contentStyle = getStyle(workbook, "content");
		Iterator<T> it;
		// 遍历集合数据，产生数据行
		it = list.iterator();
		
		int index = titleRow;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			row.setHeight((short) 400);// 行高设置成25px
			T t = (T) it.next();
			Boolean flag = tranFieldToPer(t);// 需要处理%列
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fieldSource = t.getClass().getDeclaredFields();
			Field[] fields = null;
			fields = fieldSource;
			for (short i = 0; i < fields.length - hideColumn; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(contentStyle);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Integer) {
						int intValue = (Integer) value;
						cell.setCellValue(intValue);
					} else if (value instanceof Float) {
						if (flag && judgeField(fieldName)) {
							cell.setCellValue(StringUtil.strFloatToPer(String.valueOf(value)));// 转换成%
						} else {
							float fValue = (Float) value;
							cell.setCellValue(String.format("%.2f", fValue));
						}

					} else if (value instanceof Double) {
						double dValue = (Double) value;
						cell.setCellValue(dValue);
					} else if (value instanceof Long) {
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value != null) {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					} else if (value == null) {
						textValue = "";
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							XSSFRichTextString richString = new XSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
				}
				sheet.autoSizeColumn((short) i);// 设置自动调整列宽
			}
		}
		// 第mergeColumn列相同数据合并单元格
		if (mergeColumn != -1) {
			addMergedRegion(sheet, mergeColumn, 4, sheet.getLastRowNum(), workbook);// 就是合并第一列的所有相同单元格
		}
		// 添加这个就是为了合并多列单元格
		if (MergeColumn != -1) {
			addMergedRegion(sheet, MergeColumn, 2, sheet.getLastRowNum(), workbook);// 就是合并第一列的所有相同单元格
		}
		// 添加这个就是为了合并多列单元格
		if (MergeColumn0 != -1) {
			addMergedRegion(sheet, MergeColumn0, 2, sheet.getLastRowNum(), workbook);// 就是合并第一列的所有相同单元格
		}
	}

	/**
	 * 导出2007版Excel
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param list
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的:
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 * @param mergeColumn
	 *            合并单元格列数，-1表示没有合并单元格
	 * @param hideColumn
	 *            隐藏表格内容的最后几列，0表示没有隐藏
	 * @param titleRow
	 *            表头行数
	 */
	@SuppressWarnings({ "unchecked" })
	private void export2007Excel(XSSFWorkbook workbook, String title, String[] headerSource, Collection<T> list,
			String pattern, Integer mergeColumn, Integer MergeColumn, Integer MergeColumn0, Integer hideColumn,
			Integer titleRow, Integer isList) {
		XSSFSheet sheet = workbook.createSheet(title);
		if (titleRow == null) {
			titleRow = 0;
		}
		dealHeader(headerSource, titleRow, workbook, sheet, title);
		XSSFRow row = null;
		XSSFCellStyle contentStyle = getStyle(workbook, "content");
		Iterator<T> it;
		// 遍历集合数据，产生数据行
		it = list.iterator();
		
		int index = titleRow;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			row.setHeight((short) 400);// 行高设置成25px
			List<String> aa = (List<String>) it.next();
			for (short i = 0; i < aa.size(); i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(contentStyle);
				try {
					String value = aa.get(i);
					String textValue = value.toString();
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							XSSFRichTextString richString = new XSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} finally {
				}
				sheet.autoSizeColumn((short) i);// 设置自动调整列宽
			}
		}
		// 第mergeColumn列相同数据合并单元格
		if (mergeColumn != -1) {
			addMergedRegion(sheet, mergeColumn, 4, sheet.getLastRowNum(), workbook);// 就是合并第一列的所有相同单元格
		}
		// 添加这个就是为了合并多列单元格
		if (MergeColumn != -1) {
			addMergedRegion(sheet, MergeColumn, 2, sheet.getLastRowNum(), workbook);// 就是合并第一列的所有相同单元格
		}
		// 添加这个就是为了合并多列单元格
		if (MergeColumn0 != -1) {
			addMergedRegion(sheet, MergeColumn0, 2, sheet.getLastRowNum(), workbook);// 就是合并第一列的所有相同单元格
		}
	}
	
	/**
	 * 将输出流写入工作薄(2007版)
	 * 
	 * @param workbook
	 * @param out
	 */
	private void write2007Out(XSSFWorkbook workbook, OutputStream out) {
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 去除需要隐藏的表头
	 * 
	 * @param headers
	 * @return
	 */
	private String[] hidHeader(String[] headers) {
		List<String> list = new ArrayList<String>();
		for (short i = 0; i < headers.length; i++) {
			if (!headers[i].contains("hidden")) {
				list.add(headers[i]);
			}
		}
		String[] arr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = (String) list.get(i);
		}
		return arr;
	}

	/**
	 * 设置标题样式
	 * 
	 * @param workbook
	 * @return
	 */
	private XSSFCellStyle getTitleStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中

		// 生成另一个字体
		XSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12); // 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
		// 把字体应用到当前的样式
		style.setFont(font);

		return style;
	}

	/**
	 * 设置表格样式
	 * 
	 * @param workbook
	 * @param type
	 * @return
	 */
	private XSSFCellStyle getStyle(XSSFWorkbook workbook, String type) {
		XSSFCellStyle style = workbook.createCellStyle();

		style.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色

		style.setBorderLeft(CellStyle.BORDER_THIN); // 左边边框
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边边框颜色

		style.setBorderRight(CellStyle.BORDER_THIN); // 右边边框
		style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边边框颜色

		style.setBorderTop(CellStyle.BORDER_THIN); // 上边边框
		style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边边框颜色

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中

		// 生成另一个字体
		XSSFFont font = workbook.createFont();
		font.setFontName("宋体");

		switch (type) {
		case "header":
			font.setFontHeightInPoints((short) 10); // 设置字体大小
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
			break;
		case "content":
			font.setFontHeightInPoints((short) 10); // 设置字体大小
			break;
		default:
			break;
		}
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 *            要合并单元格的excel 的sheet
	 * @param cellLine
	 *            要合并的列
	 * @param startRow
	 *            要合并列的开始行
	 * @param endRow
	 *            要合并列的结束行
	 */
	private static void addMergedRegion(XSSFSheet sheet, int cellLine, int startRow, int endRow,
			XSSFWorkbook workBook) {
		XSSFCellStyle style = workBook.createCellStyle(); // 样式对象

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		// 获取第一行的数据,以便后面进行比较
		String s_will = sheet.getRow(startRow).getCell(cellLine).getStringCellValue();

		int count = 0;
		boolean flag = false;
		int merge_start_row = startRow;
		for (int i = startRow + 1; i <= endRow; i++) {
			String s_current = sheet.getRow(i).getCell(cellLine).getStringCellValue();
			if (s_will.equals(s_current)) {
				flag = true;
				count++;
			} else {
				if (flag) {
					CellRangeAddress cra = new CellRangeAddress(merge_start_row, merge_start_row + count, cellLine,
							cellLine);
					sheet.addMergedRegion(cra);
				}
				flag = false;
				count = 0;
				merge_start_row = i;
			}
			s_will = s_current;

			// 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
			if (i == endRow && count > 0) {
				CellRangeAddress cra = new CellRangeAddress(endRow - count, endRow, cellLine, cellLine);
				sheet.addMergedRegion(cra);
			}
		}
	}

	/**
	 * 生成表格名称（只合并单元格，不赋值）
	 * 
	 * @param sheet
	 * @param headerSource
	 * @return
	 */
	private Cell mergeTitle(XSSFSheet sheet, String[] headers) {
		Row titleRow = sheet.createRow(0); // 创建一个行
		titleRow.setHeightInPoints(25);
		Cell titleCell = titleRow.createCell(0);
		int len = headers.length - 1;
		for (int i = 0; i < headers.length; i++) {
			if (headers[i].contains("hidden")) {
				len--;
			}
		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, len));// 起始行，结束行，起始列，结束列
		return titleCell;
	}

	/**
	 * 
	 * 两行表头处理
	 * 
	 * @param headers
	 *            格式：抹尘房[数量,总用时,平均用时,排名]
	 * @param titleRow
	 *            表头共有几行
	 * @param workbook
	 * @param sheet
	 * @param title
	 *            标题名
	 */
	private void dealHeader(String[] headers, Integer titleRow, XSSFWorkbook workbook, XSSFSheet sheet, String title) {
		// 生成样式
		XSSFCellStyle titleStyle = getTitleStyle(workbook);
		XSSFCellStyle headerStyle = getStyle(workbook, "header");

		if (titleRow == 2) {
			List<String> list1 = new ArrayList<String>();// 记录第一行
			List<String> list2 = new ArrayList<String>();// 记录第二行
			String ele = null;
			int num = 0;// 记录需要合并表头的列数(连续)
			boolean flag = true;
			int sumLen = 0;// 子列长度累加
			List<String> listMerge = new ArrayList<String>();
			for (int i = 0; i < headers.length; i++) {
				ele = headers[i];
				if (ele.contains("[") && ele.contains("]")) {
					flag = false;
					int beginIndex = ele.indexOf('[');
					int endIndex = ele.indexOf(']');
					String firstTitle = ele.substring(0, beginIndex);// 第一行表头名解析，格式：抹尘房
					String tmp = ele.substring(beginIndex + 1, endIndex);// 第一行表头名解析，格式：数量,总用时,平均用时,排名
					String[] arr = tmp.split(",");// 将[]中内容解析成数组
					int len = arr.length;

					int a1 = i + sumLen;
					int a2 = a1 + len - 1;
					sumLen += len - 1;
					listMerge.add(a1 + "," + a2);// 格式："起始列,终止列"

					for (int j = 0; j < len; j++) {
						list1.add(firstTitle);// 重复补齐，在创建cell时再合并
						list2.add(arr[j]);
					}
				} else {
					if (flag) {
						num++;
					}
					list1.add(ele);
					list2.add(ele);
				}
			}

			String[] tmpArr = new String[list1.size()];
			list1.toArray(tmpArr);
			createTitleRow(sheet, tmpArr, titleStyle, title);
			createHeaderRow(sheet, tmpArr, headerStyle, 1, listMerge);// 第一行
			list2.toArray(tmpArr);
			createHeaderRow(sheet, tmpArr, headerStyle, 2, null);// 第二行

			// 合并表头
			for (int i = 0; i < num; i++) {// 合并行
				addMergedRegion(sheet, i, 1, 2, workbook);
			}
		} else {
			createTitleRow(sheet, headers, titleStyle, title);
			createHeaderRow(sheet, headers, headerStyle, 1, null);
		}
	}

	/**
	 * 合并单元格，设置表名样式
	 * 
	 * @param sheet
	 * @param headers
	 * @param titleStyle
	 * @param title
	 */
	private void createTitleRow(XSSFSheet sheet, String[] headers, XSSFCellStyle titleStyle, String title) {
		Cell titleCell = mergeTitle(sheet, headers);// 合并单元格，设置表名样式
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(title);
	}

	/**
	 * 产生表格标题行
	 * 
	 * @param sheet
	 * @param headers
	 * @param headerStyle
	 * @param mapMerge
	 *            表头需要合并列
	 */
	private void createHeaderRow(XSSFSheet sheet, String[] headers, XSSFCellStyle headerStyle, Integer index,
			List<String> listMerge) {
		// 产生表格标题行
		XSSFRow row = sheet.createRow(index);// 从第index行开始生成表格
		row.setHeight((short) 500);// 行高设置成25px

		String[] headerArr = hidHeader(headers);
		for (int i = 0; i < headerArr.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			XSSFRichTextString text = new XSSFRichTextString(headerArr[i]);
			cell.setCellValue(text);
		}

		if (listMerge != null) {
			for (String ele : listMerge) {
				String[] arr = ele.split(",");
				sheet.addMergedRegion(new CellRangeAddress(1, 1, Integer.parseInt(arr[0]), Integer.parseInt(arr[1])));// 起始行，结束行，起始列，结束列
			}
		}
	}

	/**
	 * 需要转化%列的类
	 * 
	 * @param t
	 * @return
	 */
	private Boolean tranFieldToPer(T t) {
		Boolean flag = false;
		Class<? extends Object> cla = t.getClass();
		List<Object> list = new ArrayList<Object>();
		list.add(CheckHouse.class);
		if (list.contains(cla)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断需要转换的字段属性
	 * 
	 * @param fieldName
	 * @return
	 */
	private Boolean judgeField(String fieldName) {
		Boolean flag = false;
		List<String> list = new ArrayList<String>();
		list.add("timeOutRate");
		list.add("house_eff");
		list.add("house_serv_eff");
		list.add("reject_dust_eff");
		list.add("reject_night_eff");
		list.add("reject_leave_eff");
		list.add("workEffeciencyAvg");
		list.add("efficiency");
		if (list.contains(fieldName)) {
			flag = true;
		}
		return flag;
	}

}
