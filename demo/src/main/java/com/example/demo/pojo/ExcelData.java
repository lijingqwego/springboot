package com.example.demo.pojo;

import java.util.List;
import java.util.Map;

public class ExcelData {

    private int maxLongOption;

    private String[] options;

    private Map<String, List<String>> linkageOptions;

    private List<?> dataList;

    public int getMaxLongOption() {
        return maxLongOption;
    }

    public void setMaxLongOption(int maxLongOption) {
        this.maxLongOption = maxLongOption;
    }

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

    public Map<String, List<String>> getLinkageOptions() {
        return linkageOptions;
    }

    public void setLinkageOptions(Map<String, List<String>> linkageOptions) {
        this.linkageOptions = linkageOptions;
    }
}
