package com.bocs.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import com.bocs.annotation.ExportExcel;

public class ExcelUtil {

	/** 总行数 */
	private int totalRows = 0;

	/** 总列数 */
	private int totalCells = 0;
	
	/** 表头信息 */
	private Map<Integer, String> columnHead;

	/**
	 * @描述：验证excel文件
	 */
	public boolean validateExcel(String filePath) {
		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (filePath == null
				|| !(isExcel2003(filePath) || isExcel2007(filePath))) {
			return false;
		}

		/** 检查文件是否存在 */
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return false;
		}
		return true;
	}

	/**
	 * @描述：根据文件名读取excel文件
	 */
	public List<Object> read(String filePath) {
		List<Object> dataLst = new ArrayList<Object>();
		InputStream is = null;
		try {
			/** 验证文件是否合法 */
			if (!validateExcel(filePath)) {
				return null;
			}

			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (isExcel2007(filePath)) {
				isExcel2003 = false;
			}

			/** 调用本类提供的根据流读取的方法 */
			File file = new File(filePath);
			is = new FileInputStream(file);
			dataLst = readExcel(is, isExcel2003);
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}

		/** 返回最后读取的结果 */
		return dataLst;
	}

	/**
	 * @描述：是否是2003的excel，返回true是2003
	 */
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	/**
	 * @描述：是否是2007的excel，返回true是2007
	 */
	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}


	/** 
	 *  
	 * @Title exportExcel 
	 * @Description 导出excel，利用了Java的反射原理 
	 * @param title 
	 * @param headers 
	 * @param dataset 
	 * @param out 
	 * @param pattern 
	 * @date Dec 11, 2012 
	 */  
	public <T> void exportToExcel(String title, String[] headers,  
	        Collection<T> dataset, OutputStream out, String datePattern, String numberPattern, boolean isExcel2003) {  
		// 声明一个工作薄  
	   Workbook workbook = null;
		if(isExcel2003){
			workbook = new HSSFWorkbook(); 
		}else{
			workbook = new XSSFWorkbook();
		}
	     
	    // 生成一个表格  
	    Sheet sheet = workbook.createSheet(title);  
	    // 设置表格默认列宽度为15个字节  
	    sheet.setDefaultColumnWidth(15);  
	    // 生成一个样式  
	    CellStyle style = workbook.createCellStyle();  
	    // 设置这些样式  
	    style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
	    style.setBorderBottom(CellStyle.BORDER_THIN);  
	    style.setBorderLeft(CellStyle.BORDER_THIN);  
	    style.setBorderRight(CellStyle.BORDER_THIN);  
	    style.setBorderTop(CellStyle.BORDER_THIN);  
	    style.setAlignment(CellStyle.ALIGN_CENTER);  
	    style.setWrapText(true);
	   // style.setShrinkToFit(true);
	    // 生成一个字体  
	   org.apache.poi.ss.usermodel.Font font = workbook.createFont();  
	    font.setColor(HSSFColor.VIOLET.index);  
	    font.setFontHeightInPoints((short) 12);  
	    font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);  
	    // 把字体应用到当前的样式  
	    style.setFont(font);  
	    // 生成并设置另一个样式  
	    CellStyle style2 = workbook.createCellStyle();  
	    style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
	    style2.setFillPattern(CellStyle.SOLID_FOREGROUND);  
	    style2.setBorderBottom(CellStyle.BORDER_THIN);  
	    style2.setBorderLeft(CellStyle.BORDER_THIN);  
	    style2.setBorderRight(CellStyle.BORDER_THIN);  
	    style2.setBorderTop(CellStyle.BORDER_THIN);  
	    style2.setAlignment(CellStyle.ALIGN_CENTER);  
	    style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
	    
	    // 生成另一个字体  
	    org.apache.poi.ss.usermodel.Font font2 = workbook.createFont();  
	    font2.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_NORMAL);  
	    // 把字体应用到当前的样式  
	    style2.setFont(font2);  
	    
	    
	    CellStyle dateStyle = workbook.createCellStyle();
		short df = workbook.createDataFormat().getFormat(datePattern);
		dateStyle.cloneStyleFrom(style2);
		dateStyle.setDataFormat(df);

		CellStyle numberStyle = workbook.createCellStyle();
		short nf = HSSFDataFormat.getBuiltinFormat(numberPattern);
		numberStyle.cloneStyleFrom(style2);
		numberStyle.setDataFormat(nf);
		numberStyle.setAlignment(CellStyle.ALIGN_RIGHT);

		CellStyle stringStyle = workbook.createCellStyle();
		short sf = HSSFDataFormat.getBuiltinFormat("@");
		stringStyle.cloneStyleFrom(style2);
		stringStyle.setDataFormat(sf);
	  
	    // 声明一个画图的顶级管理器  
	    /*HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
	    // 定义注释的大小和位置,详见文档  
	    HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,  
	            0, 0, 0, (short) 4, 2, (short) 6, 5));  
	    // 设置注释内容  
	    comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));  
	    // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.  
	    comment.setAuthor("leno");  */
	  
	    // 产生表格标题行  
	    Row row = sheet.createRow(0);  
	    for (int i = 0; i < headers.length; i++) {  
	        Cell cell = row.createCell(i);  
	        cell.setCellStyle(style);  
	        RichTextString text = null;
	        if(isExcel2003){
	        	text = new HSSFRichTextString(headers[i]);
	        }else{
	        	text = new XSSFRichTextString(headers[i]);
	        }
	        cell.setCellValue(text);  
	    }  
	  
	    // 遍历集合数据，产生数据行  
	    Iterator<T> it = dataset.iterator();  
	    int index = 0;  
	    while (it.hasNext()) {  
	        index++;  
	        row = sheet.createRow(index);  
	        Object t = it.next(); //这里不要使用泛型强制转换  
	        // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
	        Class<? extends Object> tCls = t.getClass(); 
	        Field[] fields = tCls.getDeclaredFields();  
	        int cellIndex = 0;//excel单元格指针
	        for (int i = 0; i < fields.length; i++) {
	        	Field field = fields[i];
	        	if(field.isAnnotationPresent(ExportExcel.class)){
	        		ExportExcel exprotExcel = field.getAnnotation(ExportExcel.class);
	        		if(! exprotExcel.value()){//此属性不输出到excel
	        			continue;//跳到下一个属性
	        		}
	        	}
	           Cell cell = row.createCell(cellIndex++);  
	            cell.setCellStyle(style2);  
	            String fieldName = field.getName();  
	            String getMethodName = "get"  
	                    + fieldName.substring(0, 1).toUpperCase()  
	                    + fieldName.substring(1);  
            	try {  
            		 
            		Method getMethod = tCls.getMethod(getMethodName, new Class[] {});  
            		Object value = getMethod.invoke(t, new Object[] {});  
            		// 判断值的类型后进行强制类型转换  
            		if(value == null || "".equals(value)){  
            			value = "";  
            		}else if (value instanceof Integer) {  
            			int intValue = (Integer) value;  
            			cell.setCellValue(intValue);  
            		} else if (value instanceof Float) {  
            			float fValue = (Float) value;  
            			cell.setCellStyle(numberStyle);
            			cell.setCellValue(fValue);  
            		} else if (value instanceof Double) {  
            			double dValue = (Double) value;  
            			cell.setCellStyle(numberStyle);
            			cell.setCellValue(dValue);  
            		} else if (value instanceof Long) {  
            			long longValue = (Long) value;  
            			cell.setCellValue(longValue);  
            		} else if (value instanceof Date) {  
            			Date date = (Date) value;  
            			cell.setCellStyle(dateStyle);
            			cell.setCellValue(date);
            		} else if (value instanceof Boolean){
            			boolean bValue = (Boolean) value;
            			if(field.isAnnotationPresent(ExportExcel.class)){
        	        		ExportExcel exprotExcel = field.getAnnotation(ExportExcel.class);
        	        		String[] booleanTextArray = exprotExcel.booleanValue();
        	        		if(bValue){
        	        			cell.setCellValue(booleanTextArray[0]);
        	        		}else{
        	        			cell.setCellValue(booleanTextArray[1]);
        	        		}
        	        	}else{
        	        		if(bValue){
        	        			cell.setCellValue("是");
        	        		}else{
        	        			cell.setCellValue("否");
        	        		}
        	        	}
            		} else {  
            			// 其它数据类型都当作字符串简单处理  
            			String textValue = value.toString();  
            			// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
            			if (textValue != null) {  
            				Pattern p = Pattern.compile("^//d+(//.//d+)?{1}");  
            				Matcher matcher = p.matcher(textValue);  
            				if (matcher.matches()) {  
            					// 是数字当作double处理  
            					cell.setCellValue(Double.parseDouble(textValue));  
            				} else { 
            					RichTextString richString = null;
            					if(isExcel2003){
            						richString = new HSSFRichTextString(textValue);  
            					}else{
            						richString = new XSSFRichTextString(textValue);  
            					}
            					if(isExcel2003){
            						HSSFFont font3 = (HSSFFont) workbook.createFont();  
            						font3.setColor(HSSFColor.BLUE.index);  
            						richString.applyFont(font3);  
            					}else{
            						XSSFFont font3 = (XSSFFont) workbook.createFont(); 
            						font3.setColor(HSSFColor.BLUE.index);
            						richString.applyFont(font3); 
            					}
            					cell.setCellStyle(stringStyle);
            					cell.setCellValue(richString);  
            				}  
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
            		// 清理资源  
            		
            		
            	}  
	        }  
	    }  
	    try {  
	        workbook.write(out);  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally{
	    	try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}  
	
	/**
	 * 写入文件
	 * @param listData 
	 * @param storePath
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void writeExcel(List<Object> listData, OutputStream out, String sheetName, String datePattern, String numberPattern, boolean isExcel2003) {
		Workbook workbook = null;
		try {
			if (isExcel2003) {
				workbook = new HSSFWorkbook();
			} else {
				workbook = new XSSFWorkbook();
			}

			CellStyle dateStyle = workbook.createCellStyle();
			short df = workbook.createDataFormat().getFormat(datePattern);
			dateStyle.setDataFormat(df);

			CellStyle numberStyle = workbook.createCellStyle();
			short nf = HSSFDataFormat.getBuiltinFormat(numberPattern);
			numberStyle.setDataFormat(nf);

			CellStyle stringStyle = workbook.createCellStyle();
			short sf = HSSFDataFormat.getBuiltinFormat("@");
			stringStyle.setDataFormat(sf);

			CellStyle cellStyle = workbook.createCellStyle();

			// 一、设置背景色：
			// cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			// cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// 二、设置边框:
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
			// 三、设置居中:
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
			// 四、设置字体:
			org.apache.poi.ss.usermodel.Font headFont = workbook.createFont();
			headFont.setFontName("黑体");
			headFont.setFontHeightInPoints((short) 16);// 设置字体大小
			// cellStyle.setFont(font);//选择需要用到的字体格式

			org.apache.poi.ss.usermodel.Font font = workbook.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 10);// 设置字体大小
			// cellStyle.setFont(font);//选择需要用到的字体格式

			// 五、设置列宽:

			// sheet.setColumnWidth(0, 3766); //第一个参数代表列id(从0开始),第2个参数代表宽度值 参考
			// ："2012-08-10"的宽度为2500

			// 五、设置自动换行:
			cellStyle.setWrapText(true);// 设置自动换行

			// HSSFWorkbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet(sheetName);
			// 六、设置表格默认列宽度为16个字节
			// sheet.setDefaultColumnWidth((short) 16);
			// sheet.setDefaultRowHeight((short) 11);
			// sheet.setColumnWidth(2, 7000); //第一个参数代表列id(从0开始),第2个参数代表宽度值 参考
			// ："2012-08-10"的宽度为2500
			sheet.setColumnWidth(6, 7000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值 参考
											// ："2012-08-10"的宽度为2500

			// 声明一个画图的顶级管理器
			Drawing patriarch = sheet.createDrawingPatriarch();
			// 定义注释的大小和位置,详见文档
			// HSSFComment comment = patriarch.createComment(new
			// HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			// 设置注释内容
			// comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			// comment.setAuthor("leno");
			// 行循环
			for (int i = 0; i < listData.size(); i++) {
				List<Object> data = (List<Object>) listData.get(i);
				Row row = sheet.createRow(i);
				// 列循环
				for (int j = 0; j < data.size(); j++) {
					Cell cell = row.createCell(j);
					if (i == 0) {
						// 标题行
						cellStyle.setFont(headFont);
					} else {
						cellStyle.setFont(font);
					}
					cell.setCellStyle(cellStyle);
					Object value = data.get(j);
					if (value instanceof Date) {
						Date date = (Date) value;
						cell.setCellStyle(dateStyle);
						cell.setCellValue(date);
						sheet.setColumnWidth(j, 3000);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(j, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						ClientAnchor anchor = null;
						if (isExcel2003) {
							anchor = new HSSFClientAnchor(0, 0, 1023, 255,
									(short) 6, i, (short) 6, i);
						} else {
							anchor = new XSSFClientAnchor(0, 0, 1023, 255,
									(short) 6, i, (short) 6, i);
						}
					//	anchor.setAnchorType(ClientAnchor.MOVE_DONT_RESIZE);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, Workbook.PICTURE_TYPE_JPEG));
					} else if (value instanceof Long) {
						long dValue = (Long) value;
						// cell.setCellStyle(numberStyle);
						cell.setCellValue(dValue);
					} else if (value instanceof Double) {
						double dValue = (Double) value;
						cell.setCellStyle(numberStyle);
						cell.setCellValue(dValue);
					} else if (value instanceof Float) {
						double dValue = (Float) value;
						cell.setCellStyle(numberStyle);
						cell.setCellValue(dValue);
					}else if (value instanceof Integer) {
						int dValue = (Integer) value;
						// cell.setCellStyle(numberStyle);
						cell.setCellValue(dValue);
					} else {
						RichTextString richString = null;
						if (isExcel2003) {
							richString = new HSSFRichTextString(
									String.valueOf(value));
						} else {
							richString = new XSSFRichTextString(
									String.valueOf(value));
						}
						cell.setCellStyle(stringStyle);
						cell.setCellValue(richString);
					}
				}
			}
			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取图标的输出流
	 * @param pathOfPicture
	 * @return
	 */
	public ByteArrayOutputStream handlePicture(String pathOfPicture) {
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		BufferedImage bufferImg = null;
		File imageFile = new File(pathOfPicture);
		try {
			bufferImg = ImageIO.read(imageFile);
			ImageIO.write(bufferImg, "jpeg", byteArrayOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageFile.delete();
		return byteArrayOut;
	}
	
	/**
	 * 构造3D饼状图
	 * @param dataset
	 * @param imageName
	 * @param titleName
	 */
	public void createPieChart3D(DefaultPieDataset dataset, String imageName, String titleName) {
		DefaultPieDataset data = dataset;
		JFreeChart chart = ChartFactory.createPieChart3D("", data, true, true, true);
		chart.setBorderPaint(Color.GRAY);
		chart.setBorderVisible(true);
		TextTitle textTile = new TextTitle(titleName, new Font("黑体", 10, 22));
		textTile.setMargin(30, 0, 0, 0);
		chart.setTitle(textTile);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("SimSun", 10, 16));
		chart.getLegend().setItemFont(new Font("SimSun", 10, 16));
		plot.setNoDataMessage("");
		plot.setCircular(false);
		plot.setOutlinePaint(Color.WHITE);
		plot.setShadowPaint(Color.white);
		plot.setBackgroundPaint(null);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} : ({1}，{2}))", 
				new DecimalFormat(), new DecimalFormat("0.00%")));
		
		try {
			File imageFile = new File(getImageStorePath() + imageName);
			if (!imageFile.getParentFile().exists()) {
				imageFile.getParentFile().mkdirs();
			}
			OutputStream os = new FileOutputStream(imageFile);
			try {
				ChartUtilities.writeChartAsJPEG(os, chart, 1000, 600);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建3D柱状图
	 * 
	 * @param dataset
	 * @param imageName
	 * @param titleName
	 */
	@SuppressWarnings("deprecation")
	public void createBarChart3D(DefaultCategoryDataset dataset, String imageName, String titleName) {
		DefaultCategoryDataset data = dataset;
		JFreeChart chart = ChartFactory.createBarChart3D(titleName, "选项名称", "选择数量", data, PlotOrientation.VERTICAL, true, false, false);
		chart.getTitle().setFont(new Font("黑体", 10, 22));
		chart.getLegend().setItemFont(new Font("SimSun", 10, 20));
		CategoryPlot plot = chart.getCategoryPlot();
		//设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		//设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		//设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		plot.getDomainAxis().setLabelFont(new Font("SimSun", 10, 20));
		plot.getDomainAxis().setTickLabelFont(new Font("SimSun", 10, 20));
		plot.getRangeAxis().setLabelFont(new Font("SimSun", 10, 20));
		plot.getRangeAxis().setTickLabelFont(new Font("SimSun", 10, 20));
		plot.getRangeAxis().setUpperMargin(0.15);
		//显示每个柱的数值，并修改该数值的字体属性
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		renderer.setItemLabelFont(new Font("SimSun", 10, 20));
		renderer.setItemMargin(0.2);
		plot.setRenderer(renderer);
		
		try {
			File imageFile = new File(getImageStorePath() + imageName);
			if (!imageFile.getParentFile().exists()) {
				imageFile.getParentFile().mkdirs();
			}
			OutputStream os = new FileOutputStream(imageFile);
			try {
				ChartUtilities.writeChartAsJPEG(os, chart, 1200, 600);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getImageStorePath() {
		String rootPath = getClass().getResource("/").toString();
		rootPath = rootPath.replace("file:/", "").replace("classes", "tempImages");
		return rootPath;
	}
	/**
	 * @描述：根据流读取Excel文件,此方法能够读出excel数据的数据类型
	 */
	public List<Object> readExcel(InputStream inputStream, boolean isExcel2003) {
		List<Object> dataLst = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			if (isExcel2003) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
			dataLst = readWorkbook(wb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataLst;
	}
	/**
	 * @描述：读取数据
	 */
	private List<Object> readWorkbook(Workbook wb) {
		List<Object> dataLst = new ArrayList<Object>();
		// 表头信息
		columnHead = new HashMap<Integer, String>();;
		
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);

		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();

		// 得到Excel的列数
		if (this.totalRows >= 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		int finishReadCount = 0;
		// 循环Excel的行
		for (int rowIndex = 0; rowIndex < totalRows + 1; rowIndex++) {
			System.out.println("正在读取第"+(rowIndex+1)+"行");
			if (finishReadCount == 2) {
				break;
			}
			DataFormatter fmt =  new DataFormatter();
			Row row = sheet.getRow(rowIndex);
			if (isBlankRow(row)) {
				finishReadCount++;
				continue;
			}
			List<Object> rowList = new ArrayList<Object>();
			String cellValue = "";
			
			for (int columnIndex = 0; columnIndex < totalCells; columnIndex++) {
				System.out.println("正在读取第"+(columnIndex+1)+"列");
				// 第1行为表头信息
				if (rowIndex == 0) {
					cellValue = row.getCell(columnIndex) == null ? cellValue : row.getCell(columnIndex).getStringCellValue().trim();
					columnHead.put(columnIndex, cellValue);
				} else {
					rowList.add(getCellValue(row.getCell(columnIndex), fmt));
				}
			}
			if (rowIndex == 0) {
				dataLst.add(columnHead);
			} else {
				dataLst.add(rowList);
			}
		}
		return dataLst;
	}
	
	private boolean isBlankRow(Row row) {
		DataFormatter fmt =  new DataFormatter();
		if(row != null && row.getCell(0) != null && row.getCell(1) != null){
			Object cellVal1 = getCellValue(row.getCell(0), fmt);
			Object cellVal2 = getCellValue(row.getCell(1), fmt);
			if (cellVal1 != null && cellVal2 != null){
				String cellVal1Str = "";
				String cellVal2Str = "";
				if(cellVal1 instanceof String){
					cellVal1Str = (String)cellVal1;
				}
				if(cellVal2 instanceof String){
					cellVal2Str = (String)cellVal2;
				}
				if(StringUtils.isNotBlank(cellVal1Str) || StringUtils.isNotBlank(cellVal2Str)){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @param columnIndex
	 * @return
	 */
	private Object getCellValue(Cell cell, DataFormatter fmt) {
		Object cellValue = null;
		if(cell != null){
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)){
					cellValue = cell.getDateCellValue();
				}else{
					String value = fmt.formatCellValue(cell);
					if (! value.trim().isEmpty()) {
						if(value.indexOf(".") == -1){
							cellValue = Long.parseLong(value);
						}else{
							cellValue = Double.parseDouble(value);
						}
					}
				} 
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				try{
					cellValue = cell.getNumericCellValue();
				}catch (Exception e) {
					try {
						cellValue = cell.getStringCellValue();
					} catch (Exception e1) {
						cellValue = "";
					}
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = cell.getErrorCellValue();
				break;
			default:
				cellValue = cell.getStringCellValue();
				break;
			}
		}else{
			cellValue = "";
		}
		return cellValue;
	}
	
}
