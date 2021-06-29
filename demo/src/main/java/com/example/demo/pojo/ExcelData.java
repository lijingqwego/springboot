package com.example.demo.pojo;

import com.example.demo.drools.Person;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collection;
import java.util.List;

public class ExcelData {

    private String[] options;

    private List<?> dataList;

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
}
