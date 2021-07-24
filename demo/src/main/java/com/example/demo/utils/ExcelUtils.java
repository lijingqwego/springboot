package com.example.demo.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Vector;

import com.example.demo.gui.Constans;
import com.example.demo.pojo.Student;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelUtils {

	/**
	 * 将用户的信息导入到excel文件中去
	 *
	 * @param param 数据源
	 * @param out 输出表
	 * @param mode 模式
	 */
	public static void writeExcel(Map<String, Object> param, OutputStream out,String mode) {
		try {
			//取出参数
			Vector<Student> stuList=(Vector<Student>) param.get("students");
			String[] titles = (String[]) param.get("title");
			
			// 1.创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 1.1创建合并单元格对象
			CellRangeAddress callRangeAddress = new CellRangeAddress(0, 0, 0, 4);// 起始行,结束行,起始列,结束列
			// 1.2头标题样式
			HSSFCellStyle headStyle = createCellStyle(workbook, (short) 16);
			// 1.3列标题样式
			HSSFCellStyle colStyle = createCellStyle(workbook, (short) 13);
			// 2.创建工作表
			HSSFSheet sheet = workbook.createSheet("学生列表");
			// 2.1加载合并单元格对象
			sheet.addMergedRegion(callRangeAddress);
			// 设置默认列宽
			sheet.setDefaultColumnWidth(25);
			// 3.创建行
			// 3.1创建头标题行;并且设置头标题
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);

			// 加载单元格样式
			cell.setCellStyle(headStyle);
			cell.setCellValue("学生列表");

			// 3.2创建列标题;并且设置列标题
			HSSFRow row2 = sheet.createRow(1);
			String[] genders = { "男","女"};
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				// 加载单元格样式
				cell2.setCellStyle(colStyle);
				cell2.setCellValue(titles[i]);
			}
			//性别下拉选项
			setDropMenu(sheet,genders,2, Constans.Excel_Import_LineNumber, 2, 2);
			//如果mode=2导出数据
			if(Constans.Excel_Export_Data.equals(mode)){
				// 4.操作单元格;将用户列表写入excel
				if (stuList != null) {
					for (int i = 0; i < stuList.size(); i++) {
						// 创建数据行,前面有两行,头标题行和列标题行
						HSSFRow row3 = sheet.createRow(i + 2);
						HSSFCell cell1 = row3.createCell(0);
						cell1.setCellValue(stuList.get(i).getNo());
						HSSFCell cell2 = row3.createCell(1);
						cell2.setCellValue(stuList.get(i).getName());
						HSSFCell cell3 = row3.createCell(2);
						cell3.setCellValue("0".equals(stuList.get(i).getGender()) ? "男" : "女");
						HSSFCell cell4 = row3.createCell(3);
						cell4.setCellValue(stuList.get(i).getAge());
						HSSFCell cell5 = row3.createCell(4);
						cell5.setCellValue(stuList.get(i).getPlace());
						HSSFCell cell6 = row3.createCell(5);
						cell6.setCellValue(stuList.get(i).getDept());
					}
				}
			}
			// 5.输出
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param workbook
	 * @param fontsize
	 * @return 单元格样式
	 */
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize) {
		// TODO Auto-generated method stub
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		// 创建字体
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints(fontsize);
		// 加载字体
		style.setFont(font);
		return style;
	}

	public static Vector<Student> readExcel(InputStream inputStream, String excelFileName) {
		Vector<Student> students = new Vector<Student>();
		// 1.创建输入流
		try {
			boolean is03Excel = excelFileName.matches("^.+\\.(?i)(xls)$");
			// 1.读取工作簿
			Workbook workbook = is03Excel ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
			// 2.读取工作表
			Sheet sheet = workbook.getSheetAt(0);
			// 3.读取行
			// 判断行数大于二,是因为数据从第三行开始插入
			if (sheet.getPhysicalNumberOfRows() > 2) {
				Student student = null;
				//最多导入1000行数据
				int count=Math.min(Constans.Excel_Import_LineNumber+1, sheet.getPhysicalNumberOfRows());
				// 跳过前两行
				for (int i = 2; i < count; i++) {
					// 读取单元格
					Row row0 = sheet.getRow(i);
					student = new Student();
					// 学号
					Cell cell0 = row0.getCell(0);
					student.setNo(cell0.getNumericCellValue()+"");
					// 姓名
					Cell cell1 = row0.getCell(1);
					student.setName(cell1.getStringCellValue());
					// 性别
					Cell cell2 = row0.getCell(2);
					student.setGender("男".equals(cell2.getStringCellValue()) ? "0" : "1");
					// 年龄
					Cell cell3 = row0.getCell(3);
					student.setAge(cell3.getStringCellValue());
					// 籍贯
					Cell cell4 = row0.getCell(4);
					student.setPlace(cell4.getStringCellValue());
					// 院系
					Cell cell5 = row0.getCell(5);
					student.setDept(cell5.getStringCellValue());
					students.add(student);
				}
			}
			workbook.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return students;
	}
	
	/**
	 * 生成下拉列表
	 * @param sheet
	 * @param list
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 */
	private static void setDropMenu(Sheet sheet,String[] list,int firstRow, int lastRow, int firstCol, int lastCol){
		
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
		//生成下拉框内容
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(list);
		//绑定下拉框和作用区域
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
		//输入错误警告提示
		data_validation.setShowErrorBox(true);
		//对sheet页生效
		sheet.addValidationData(data_validation);
	}
}
