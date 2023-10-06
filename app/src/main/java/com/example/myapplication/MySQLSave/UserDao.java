package com.example.myapplication.MySQLSave;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.JDBCUtils;
import com.example.myapplication.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User loginQuery(String wAccount, String wPsw){
        String sql = "select * from User where account=? and password=?";
        Connection con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.setString(1,wAccount);
            pst.setString(2,wPsw);
            while (rs.next()){
                int id = rs.getInt(0);
                String psw = rs.getString(2);
                int status = rs.getInt(6);
               return new User(id,null,null,null,-1,null,status);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return new User(-1,null,null,null,-1,null,-1 );
    }
    public User findUserById(int userId) {
        String sql = "select * from User where id=?";
        Connection con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.setString(1,userId+"");
            while (rs.next()){
                int m_id  = rs.getInt(0);
                String m_account  = rs.getString(1);
                String m_password  = rs.getString(2);
                String m_name  = rs.getString(3);
                int m_sex  = rs.getInt(4);
                String m_telephone  = rs.getString(5);
                int m_status  = rs.getInt(6);
                return new User(m_id,m_account,m_password,m_name, m_sex,m_telephone, m_status);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return new User(-1,null,null,null,-1,null,-1 );
    }
    public void changeUserInfoById(ContentValues values, int userId, DatabaseCreator databaseCreator) {
        String sql = "update User set 'values' where id = ?";
        Connection con = JDBCUtils.getConn();
        try {
            PreparedStatement pst=con.prepareStatement(sql);;
            pst.setString(1,userId+"");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
    }

}
