package com.example.demo.excel;

import com.example.demo.pojo.ExcelData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;


public class ExcelUtils {

    /**
     * 将用户的信息导入到excel文件中去
     *
     * @param out
     */
    public static void writeExcel(ExcelData excelData, ServletOutputStream out) {
        try {
            File file = FileUtils.getFile(ResourceUtils.getFile("classpath:templates/模板.xlsm"));
            // 1.读取工作簿
            Workbook workbook = new XSSFWorkbook(file);
            // 2.选项数据
            Sheet hideSheet = workbook.getSheet("data");
            String[] cityList = excelData.getOptions();
            if (ArrayUtils.isEmpty(cityList)) {
                return;
            }
            for (int i = 0; i < cityList.length; i++) {
                Row row = hideSheet.getRow(i);
                if (row == null) {
                    row = hideSheet.createRow(i);
                }
                Cell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(cityList[i]);
            }
            List<?> dataList = excelData.getDataList();
            // 遍历集合数据，产生数据行
            Iterator<?> iterator = dataList.iterator();
            int index = 0;
            Sheet sheet = workbook.getSheetAt(0);
            while (iterator.hasNext()) {
                index++;
                Row row = sheet.createRow(index);
                Object object = iterator.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = object.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Cell cell = row.createCell(i);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class<?> aClass = object.getClass();
                    Method getMethod = aClass.getMethod(getMethodName);
                    Object value = getMethod.invoke(object);
                    cell.setCellValue(value.toString());
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

//    public static void main(String[] args) throws FileNotFoundException {
//        FileOutputStream fileOutputStream = new FileOutputStream("E:\\Code\\springboot\\demo\\src\\main\\resources\\templates\\data.xlsm");
//        ExcelData excelData = new ExcelData();
//        excelData.setOptions(new String[]{"AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "GGG", "HHH"});
//        List<Person> dataList = new ArrayList<Person>();
//        Person person = new Person();
//        person.setName("ssss");
//        person.setAge(111);
//        dataList.add(person);
//        excelData.setDataList(dataList);
//        ExcelUtils.writeExcel(excelData, fileOutputStream);
//    }
}
