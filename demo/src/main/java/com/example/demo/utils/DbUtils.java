package com.example.demo.utils;

import java.sql.*;
import java.util.List;
import java.util.Vector;

public class DbUtils {

    private static final String url = "jdbc:sqlite:test.db";
    private static final String username = "root";
    private static final String password = "asd3135";

    private static final int BATCH_SIZE = 20;

    /**
     * 加载驱动
     */
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {
        Connection connection = null;
        synchronized (DbUtils.class) {
            connection = DriverManager.getConnection(url);
            Statement stat = connection.createStatement();
            stat.executeUpdate("create table if not exists t_student (no,name,gender,age,place,dept);");
        }
        return connection;
    }

    /**
     * 查询数据
     *
     * @param sql
     * @param objects
     * @return
     */
    public static Vector<Vector<Object>> selectTable(String sql, Object[] objects) {
        Vector<Vector<Object>> list = new Vector<Vector<Object>>();
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                ps.setString(i + 1, objects[i].toString());
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> colnum = new Vector<Object>();
                colnum.add(rs.getInt(1));
                colnum.add(rs.getString(2));
                colnum.add(rs.getInt(3) == 0 ? "男" : "女");
                colnum.add(rs.getString(4));
                colnum.add(rs.getString(5));
                colnum.add(rs.getString(6));
                list.add(colnum);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 增、删、改数据操作
     *
     * @param sql
     * @param lists
     * @return
     */
    public static int batchupdateTable(String sql, List<List<String>> lists) {
        int count = 0;
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 1; i <= lists.size(); i++) {
                List<String> list = lists.get(i - 1);
                for (int j = 0; j < list.size(); j++) {
                    ps.setString(j + 1, list.get(j));
                }
                ps.addBatch();
            }
            ps.executeBatch();
            ps.clearBatch();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 增、删、改数据操作
     *
     * @param sql
     * @param objects
     * @return
     */
    public static int updateTable(String sql, Object[] objects) {
        int count = 0;
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                ps.setString(i + 1, objects[i].toString());
            }
            count = ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}
