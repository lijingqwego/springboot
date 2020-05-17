package com.example.demo.utils;

import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.mortbay.util.ajax.JSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class S1MmeInterfaceCfg {

    private static final String table = "t_location_interface_cfg";
    private static final String[] cols = {"cf1"};

    public static void main(String[] args) {
        //        insertDta();
        Map<String,String> map = new HashMap<String,String>();
        map.put("command","ADD S1APLE");
        map.put("start","1");
        map.put("end","9");
        map.put("neType","USN");
        map.put("neName","AWSANT");
        map.put("neId","000051");
        Set<String> set = getLocalIp(map);
//        System.out.println(JSON.toString(set));
        List<Map<String,String>> resultList = getCfgFromHbase(map,set);
        System.out.println(JSON.toString(resultList));
        //[{"neId":"000051","port":"7","vpn":"vpn_s1","localIp":"100.90.255.152","slot":"10","neName":"AWSANT","neType":"USN","frame":"1"}]
    }
    private static void insertDta() throws IOException {
        String table = "t_location_interface_cfg";
        String[] cols = {"cf1"};

        BufferedReader bufr = new BufferedReader(new FileReader("C:/Users/Administrator/Desktop/command.txt"));
        String line = null;
        int i = 1;
        while ((line=bufr.readLine())!= null){
            Map<String,String> map = new HashMap<String,String>();
            String[] cmdArr = line.split(":");
            map.put("command",cmdArr[0]);
            String[] cmdParam = cmdArr[1].split(",");
            for (String param : cmdParam){
                String[] arr = param.split("=");
                map.put("commandParam:"+arr[0],arr[1]);
            }
            HbaseUtils.insertData(table,StringUtils.hashKeyForDisk(cmdArr[0])+"_"+i,cols[0],map);
            i++;
        }
        bufr.close();
    }

    private static String[] getRowKey(Map<String,String> rowKeyMap){
        String commandHash = StringUtils.hashKeyForDisk(rowKeyMap.get("command"));
        String start = rowKeyMap.get("start");
        String end = rowKeyMap.get("end");
        String startRow = commandHash+"_"+start;
        String endRow = commandHash+"_"+end;
        return new String[]{startRow,endRow};
    }
    private static Set<String> getLocalIp(Map<String,String> rowKeyMap){
        Set<String> set = new HashSet<>();
        String[] rowKey = getRowKey(rowKeyMap);
        List<Map<String,String>> list = HbaseUtils.getRange(table,rowKey[0],rowKey[1],null);
        for (Map<String,String> map : list){
            String ip1 = map.get("commandParam:LOCALIPV4_1");
            String ip2 = map.get("commandParam:LOCALIPV4_2");
            set.add(ip1);
            set.add(ip2);
        }
        return set;
    }

    private static Filter createFilter(String filterName, Set<String> values){
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        for (String val:values) {
            SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("cf1"),Bytes.toBytes(filterName), CompareFilter.CompareOp.EQUAL,Bytes.toBytes(val));
            filterList.addFilter(filter);
        }
        return filterList;
    }

    private static List<Map<String,String>> getCfgFromHbase(Map<String,String> rowKeyMap,Set<String> ipSet){
        List<Map<String,String>> resultList = new ArrayList<>();
        rowKeyMap.put("command","ADD GLBSVRIP");
        String[] rowKey = getRowKey(rowKeyMap);
        Filter filter = createFilter("commandParam:IPV4",ipSet);
        List<Map<String,String>> list = HbaseUtils.getRange(table,rowKey[0],rowKey[1],filter);
        for (Map<String,String> map:list) {
            String ip = map.get("commandParam:IPV4");
            String vrfName = map.get("commandParam:VRFNAME");
            String pn = getPnByVrfName(rowKeyMap,vrfName);
            Map<String,String> result = getFrameSlotPortByPn(rowKeyMap,pn);
            result.put("localIp",ip);
            result.put("vpn",vrfName);
            result.put("neType",rowKeyMap.get("neType"));
            result.put("neName",rowKeyMap.get("neName"));
            result.put("neId",rowKeyMap.get("neId"));
            resultList.add(result);
        }
        return resultList;
    }

    private static String getPnByVrfName(Map<String,String> rowKeyMap,String vrfName){
        rowKeyMap.put("command","ADD VRFIFBIND");
        String[] rowKey = getRowKey(rowKeyMap);
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("cf1"),Bytes.toBytes("commandParam:VRFNAME"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes(vrfName));
        List<Map<String,String>> list = HbaseUtils.getRange(table,rowKey[0],rowKey[1],filter);
        return list.get(0).get("commandParam:PN");
    }

    private static Map<String,String> getFrameSlotPortByPn(Map<String,String> rowKeyMap,String pn){
        Map<String,String> result = new HashMap<String,String>();
        rowKeyMap.put("command","ADD ETHTRKMPORT");
        String[] rowKey = getRowKey(rowKeyMap);
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("cf1"),Bytes.toBytes("commandParam:PN"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes(pn));
        List<Map<String,String>> list = HbaseUtils.getRange(table,rowKey[0],rowKey[1],filter);
        String frame = list.get(0).get("commandParam:MSRN");
        String slot = list.get(0).get("commandParam:MSN");
        String port = list.get(0).get("commandParam:MPN");
        result.put("frame",frame);
        result.put("slot",slot);
        result.put("port",port);
        return result;
    }
}
