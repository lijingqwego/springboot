package com.example.demo.utils;


import com.example.demo.pojo.User;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists people;");
        stat.executeUpdate("create table people (name, occupation);");
        PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");

        prep.setString(1, "Gandhi");
        prep.setString(2, "politics");
        prep.addBatch();
        prep.setString(1, "Turing");
        prep.setString(2, "computers");
        prep.addBatch();
        prep.setString(1, "Wittgenstein");
        prep.setString(2, "smartypants");
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);

        ResultSet rs = stat.executeQuery("select * from people;");
        while (rs.next()) {
            System.out.println("name = " + rs.getString("name"));
            System.out.println("job = " + rs.getString("occupation"));
        }
        rs.close();
        conn.close();
    }

    private static final String table = "t_location_interface_cfg";
    private static final String[] cols = {"cf1"};

    enum Color{
        BLUE,RED,GREEN
    }
    public static void mainddd(String[] args) throws IOException {

        List<User> userList = new ArrayList<User>();
        userList.add(new User("B",56));
        userList.add(new User("B",12));
        userList.add(new User("A",35));
        userList.add(new User("A",30));
        userList.add(new User("A",75));
        userList.add(new User("C",25));
        userList.add(new User("C",45));
        List<String> list = new ArrayList<>();
        list.add("B");
        list.add("C");
        list.add("A");
        list.add("D");
        userList.sort(Comparator.comparingInt(o -> list.indexOf(o.getUserName())));
        Map<String, List<User>> groupBySex = userList.stream().collect(Collectors.groupingBy(User::getUserName,LinkedHashMap::new,Collectors.toList()));
        for (Map.Entry<String, List<User>> entryUser : groupBySex.entrySet()) {
            List<User> entryUserList = entryUser.getValue();
            for (User user : entryUserList) {
                System.out.println(user.getUserName()+"=>"+user.getUserAge());
            }
        }


        System.out.println("==================================================");

//
//        for (Map.Entry<String, List<User>> entryUser : groupBySex.entrySet()) {
//            List<User> entryUserList = entryUser.getValue();
//            Collections.sort(entryUserList, new Comparator<User>() {
//                @Override
//                public int compare(User o1, User o2) {
//                    return list.indexOf(o1.getUserName())-list.indexOf(o2.getUserName());
//                }
//            });
//            for (User user : entryUserList) {
//                System.out.println(user.getUserName()+"=>"+user.getUserAge());
//            }
//
//        }
//        for (User user : userList) {
//            System.out.println(user.getUserName()+"=>"+user.getUserAge());
//        }

//        Map<String,String> map1 = new HashMap<String,String>();
//        map1.put("AAA","2");
//        Map<String,String> map2 = new HashMap<String,String>();
//        map2.put("AAA","0");
//        Map<String,String> map3 = new HashMap<String,String>();
//        map3.put("AAA","2");
//        List<Map<String,String>> list = new ArrayList<>();
//        list.add(map1);
//        list.add(map2);
//        list.add(map3);
//        String key = "BBBB";
//        IntSummaryStatistics summary = list.stream()
//                .collect(Collectors.summarizingInt(map -> map.containsKey(key) ? Integer.parseInt(map.get(key)) : -1));
//        String maxValue = filterToString(summary.getMax());
//        String minValue = filterToString(summary.getMin());
//
//        System.out.println(maxValue);
//        System.out.println(minValue);
//        String[] arr = {};
//        boolean empty = ArrayUtils.isEmpty(arr);
//        System.out.println(empty);
//        Color bule = Color.BLUE;
//        System.out.println(bule.name());


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

    private static String filterToString(int value) {
        if (value == -1) {
            return "-";
        }
        return String.valueOf(value);
    }


}
