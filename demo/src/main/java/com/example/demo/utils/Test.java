package com.example.demo.utils;

import com.example.demo.encry.SymmetricEncoder;
import com.example.demo.pojo.User;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mortbay.util.ajax.JSON;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Test {

    private static final String table = "t_location_interface_cfg";
    private static final String[] cols = {"cf1"};

    public static void main(String[] args) throws IOException, JSONException {
//        Map<String,String> map = new HashMap<String,String>();
//        for (int i = 0; i < 10; i++) {
//            map.put("userId","000001"+i);
//            map.put("userName","lijing"+i);
//            map.put("userAge",20+i+"");
//            map.put("password", SymmetricEncoder.AESEncode(""+i));
//            HbaseUtils.insertData(table,"000001"+i,cols[0],map);
//        }



//        List<Map<String,String>> list = HbaseUtils.getAllData("test");
//        System.out.println(JSON.toString(list));

//        HbaseUtils.createTable(table,cols);



//        List<Map<String,String>> list = HbaseUtils.getRange(table,"0000011","0000014");
//        System.out.println(JSON.toString(list));
//        HbaseUtils.deleteTable("t_location_interface_cfg");


//        Map<String,String> map = HbaseUtils.getDataByRowKey(table,StringUtils.hashKeyForDisk("ADD S1APLE")+"_"+1);
//        System.out.println(JSON.toString(map));


//        System.out.println(StringUtils.hashKeyForDisk("ADD S1APLE"));
    }



}
