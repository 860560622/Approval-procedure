package com.example.myapplication.MySQLSave;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.Apply;
import com.example.myapplication.model.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplyDao {
    public List<Apply> findAllApplyByUserId(int userId){
        String sql = "select * from Apply where userID=?";
        Connection con = JDBCUtils.getConn();
        List<Apply> applyList = new ArrayList<>();
        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,userId+"");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                int m_id  = rs.getInt(0);
                int m_userID  = rs.getInt(1);
                int m_classID  = rs.getInt(2);
                String m_reason  = rs.getString(3);
                int m_state  = rs.getInt(4);
                String m_backReason  = rs.getString(5);
                int m_confirm = rs.getInt(6);
               // Apply apply = new Apply(m_id,m_userID,m_classID,m_reason,m_state,m_backReason,m_confirm);
            //   applyList.add(apply);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return applyList;
    }
}
