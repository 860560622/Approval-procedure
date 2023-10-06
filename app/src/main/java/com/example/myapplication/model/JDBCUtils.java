package com.example.myapplication.model;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    private static final String TAG = "mysql11111";
    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.v(TAG, "加载JDBC驱动成功");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "加载JDBC驱动失败");
            e.printStackTrace();
        }

    }

    public static Connection getConn() {
        Connection  conn = null;
        try {
            conn= DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/lls?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","921127");
            Log.d(TAG, "数据库连接成功");
        }catch (Exception exception){
            Log.d(TAG, "数据库连接失败");
            exception.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}