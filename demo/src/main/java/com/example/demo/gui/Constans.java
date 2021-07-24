package com.example.demo.gui;

public class Constans {

    public static final String[] COLNUM_NAME = {"编号：", "姓名：", "性别：", "年龄：", "籍贯：", "院系："};

    public static final String[] TITLES = {"编号", "姓名", "性别", "年龄", "籍贯", "院系"};

    public static final String SELECT_SQL = "select * from t_student;";

    //Excel导出模板
    public static final String Excel_Export_Template = "1";
    //Excel导出数据
    public static final String Excel_Export_Data = "2";
    //Excel最大导入行数
    public static final int Excel_Import_LineNumber = 1000;

    /**
     * 事件监听动作标识
     *
     * @author lijing
     */
    public static final class Action {

        public static final String SEARCH = "1";//搜索

        public static final String SELECT_ALL = "2";//查询全部

        public static final String DIALOG_ADD = "3";//添加对话框

        public static final String ADD = "4";//添加

        public static final String CANCLE = "5";//取消

        public static final String DIALOG_UPDATE = "6";//更新对话框

        public static final String UPDATE = "7";//更新

        public static final String DELETE = "8";//删除

        public static final String EXPORT = "9";//导出

        public static final String IMPORT = "10";//导入
    }
}
