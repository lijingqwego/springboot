package com.example.demo.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.ExcelData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


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

    /**
     * 将用户的信息导入到excel文件中去
     *
     * @param out
     */
    public static void writeExcel2(ExcelData excelData, OutputStream out) {
        try {
            File file = FileUtils.getFile(ResourceUtils.getFile("classpath:templates/模板1.xlsm"));
            // 1.读取工作簿
            Workbook workbook = new XSSFWorkbook(file);
            // 2.选项数据
            Sheet hideSheet = workbook.getSheet("data");
            Map<String,List<String>> linkageMap = excelData.getLinkageOptions();
            if (MapUtils.isEmpty(linkageMap)) {
                return;
            }
            // 初始化选项数据单元格
            Set<String> provinceList = linkageMap.keySet();
            for (int i=0;i<excelData.getMaxLongOption();i++) {
                Row row = hideSheet.createRow(i);
                for (int j = 0; j < excelData.getMaxLongOption(); j++) {
                    row.createCell(j);
                }
            }
            // 填充选项数据
            Iterator<String> iterator = provinceList.iterator();
            int index = 0;
            Row row = hideSheet.getRow(0);
            while (iterator.hasNext()) {
                String province = iterator.next();
                Cell provinceCell = row.getCell(index);
                provinceCell.setCellValue(province);
                List<String> cityList = linkageMap.get(province);
                for (int j = 0; j < cityList.size(); j++) {
                    Row cityRow = hideSheet.getRow(j+1);
                    Cell cell = cityRow.getCell(index);
                    cell.setCellValue(cityList.get(j));
                }
                index++;
            }
            List<?> dataList = excelData.getDataList();
            if (CollectionUtils.isNotEmpty(dataList)) {
                writeData(workbook, dataList);
            }
            // 5.输出
            workbook.write(out);
            workbook.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeData(Workbook workbook, List<?> dataList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
    }

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\Code\\springboot\\demo\\src\\main\\resources\\templates\\data.xlsm");
        File file = FileUtils.getFile("E:\\Code\\springboot\\demo\\src\\main\\resources\\templates\\area.json");
        String readFileToString = FileUtils.readFileToString(file, "UTF-8");
        JSONArray jsonArray = JSONArray.parseArray(readFileToString);
        Map<String, List<String>> linkageMap = new HashMap<>();
        int maxCity = 0;
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject)object;
            String citysTxt = JSONArray.toJSONString(jsonObject.get("citys"));
            List<String> cityList = JSONArray.parseArray(citysTxt, String.class);
            if(cityList.size()>maxCity) {
                maxCity = cityList.size();
            }
            linkageMap.put(jsonObject.getString("province"),cityList);
        }
        ExcelData excelData = new ExcelData();
        excelData.setLinkageOptions(linkageMap);
        excelData.setMaxLongOption(maxCity);
        ExcelUtils.writeExcel2(excelData, fileOutputStream);
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
