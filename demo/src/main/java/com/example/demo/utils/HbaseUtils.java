package com.example.demo.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HbaseUtils {

    private static Admin admin;

    /**
     * 初始化连接
     * @return
     */
    public static Connection initHbase() {
        Connection con = null;
        try {
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.property.clientPort", "2181");
            configuration.set("hbase.zookeeper.quorum", "127.0.0.1");
            //集群配置↓
            //configuration.set("hbase.zookeeper.quorum", "101.236.39.141,101.236.46.114,101.236.46.113");
            configuration.set("hbase.master", "127.0.0.1:60000");
            con = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }


    /**
     * 创建表
     * @param tableNmae
     * @param cols
     */
    public static void createTable(String tableNmae, String[] cols) {
        try {
            TableName tableName = TableName.valueOf(tableNmae);
            admin = initHbase().getAdmin();
            if (admin.tableExists(tableName)) {
                System.out.println("表已存在！");
            } else {
                HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
                for (String col : cols) {
                    HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                    hTableDescriptor.addFamily(hColumnDescriptor);
                }
                admin.createTable(hTableDescriptor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入数据
     * @param tableName
     * @param rowKey
     * @param cf
     * @param map
     */
    public static void insertData(String tableName, String rowKey,String cf, Map<String,String> map) {
        try {
            TableName tablename = TableName.valueOf(tableName);
            Put put = new Put(rowKey.getBytes());
            //参数：1.列族名  2.列名  3.值
            for (Map.Entry<String,String> entry : map.entrySet()) {
                put.addColumn(cf.getBytes(), entry.getKey().getBytes(), entry.getValue().getBytes()) ;
            }
            Table table = initHbase().getTable(tablename);
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据rowKey进行查询
     * @param tableName
     * @param rowKey
     * @return
     */
    public static Map<String,String> getDataByRowKey(String tableName, String rowKey) {
        Map<String,String> map = new HashMap<String,String>();
        try {
            Table table = initHbase().getTable(TableName.valueOf(tableName));
            Get get = new Get(rowKey.getBytes());
            //先判断是否有此条数据
            if(!get.isCheckExistenceOnly()){
                Result result = table.get(get);
                for (Cell cell : result.rawCells()){
                    String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                    String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    map.put(colName,value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询指定单cell内容
     * @param tableName
     * @param rowKey
     * @param family
     * @param col
     * @return
     */
    public static String getCellData(String tableName, String rowKey, String family, String col){
        try {
            Table table = initHbase().getTable(TableName.valueOf(tableName));
            Get get = new Get(rowKey.getBytes());
            if(!get.isCheckExistenceOnly()){
                get.addColumn(Bytes.toBytes(family),Bytes.toBytes(col));
                Result res = table.get(get);
                byte[] resByte = res.getValue(Bytes.toBytes(family), Bytes.toBytes(col));
                return  Bytes.toString(resByte);
            }else{
                return  "查询结果不存在";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "出现异常";
    }

    /**
     * 查询指定表名中所有的数据
     * @param tableName
     * @return
     */
    public static List<Map<String,String>> getAllData(String tableName){
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        try {
            Table table = initHbase().getTable(TableName.valueOf(tableName));
            ResultScanner results = table.getScanner(new Scan());
            for (Result result : results){
                Map<String,String> map = new HashMap<String,String>();
                for(Cell cell : result.rawCells()){
                    String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                    //String family =  Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength());
                    String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                    String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    map.put(colName,value);
                }
                list.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 删除指定cell数据
     * @param tableName
     * @param rowKey
     */
    public static void deleteByRowKey(String tableName, String rowKey) {
        try {
            Table table = initHbase().getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            //删除指定列
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表
     * @param tableName
     */
    public static void deleteTable(String tableName){
        try {
            TableName tablename = TableName.valueOf(tableName);
            admin = initHbase().getAdmin();
            admin.disableTable(tablename);
            admin.deleteTable(tablename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
