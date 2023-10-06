package com.example.myapplication.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.model.MyDatabaseHelper;
import com.example.myapplication.model.User;

//数据库操作
public class DatabaseCreator {
    private MyDatabaseHelper dbHelper;
    public MyDatabaseHelper getDbHelper() {
        return dbHelper;
    }
    public void createDb(Context context){
        dbHelper = new MyDatabaseHelper(context,"Data.dp",null,29);
        dbHelper.getWritableDatabase();
    }

    public void addData(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //申请数据1
        ContentValues values = new ContentValues();
        values.put("userID",1);
        values.put("classID",1);
        values.put("reason","课程冲突");
        values.put("state",2);
        values.put("backReason","null");
        values.put("confirm",0);
        values.put("image","null");
        db.insert("Apply",null,values);
        values.clear();
        //2
        values.put("userID",1);
        values.put("classID",2);
        values.put("reason","生病了");
        values.put("state",2);
        values.put("backReason","null");
        values.put("confirm",0);
        values.put("image","null");
        db.insert("Apply",null,values);
        values.clear();
        //3
        values.put("userID",1);
        values.put("classID",3);
        values.put("reason","生病了");
        values.put("state",4);
        values.put("backReason","null");
        values.put("confirm",0);
        values.put("image","null");
        db.insert("Apply",null,values);
        values.clear();
        //4
        values.put("userID",1);
        values.put("classID",1);
        values.put("reason","课程冲突");
        values.put("state",5);
        values.put("backReason","null");
        values.put("confirm",0);
        values.put("image","null");
        db.insert("Apply",null,values);
        values.clear();
        //5
        values.put("userID",1);
        values.put("classID",2);
        values.put("reason","课程冲突");
        values.put("state",2);
        values.put("backReason","null");
        values.put("confirm",0);
        values.put("image","null");
        db.insert("Apply",null,values);
        values.clear();
        //6
        values.put("userID",1);
        values.put("classID",3);
        values.put("reason","课程冲突");
        values.put("state",5);
        values.put("backReason","null");
        values.put("confirm",0);
        values.put("image","null");
        db.insert("Apply",null,values);
        values.clear();
        //7
        values.put("userID",1);
        values.put("classID",1);
        values.put("reason","课程冲突");
        values.put("state",4);
        values.put("backReason","null");
        values.put("confirm",0);
        values.put("image","null");
        db.insert("Apply",null,values);
        values.clear();
        //8
        values.put("userID",1);
        values.put("classID",2);
        values.put("reason","课程冲突");
        values.put("state",4);
        values.put("backReason","null");
        values.put("confirm",0);
        values.put("image","null");
        db.insert("Apply",null,values);
        values.clear();
        //用户数据
        //学生1
        values.put("account","1122");
        values.put("password","11");
        values.put("name","Lily");
        values.put("sex",0);
        values.put("telephone","11222");
        values.put("status",1);
        db.insert("User",null,values);
        values.clear();
        //教师2
        values.put("account","66");
        values.put("password","66");
        values.put("name","Ase");
        values.put("sex",0);
        values.put("telephone","55555");
        values.put("status",2);
        db.insert("User",null,values);
        values.clear();
        //主管教师3
        values.put("account","77");
        values.put("password","77");
        values.put("name","Uwe");
        values.put("sex",1);
        values.put("telephone","666666");
        values.put("status",3);
        db.insert("User",null,values);
        values.clear();
        //教师4
        values.put("account","2333");
        values.put("password","2333");
        values.put("name","QWQ");
        values.put("sex",0);
        values.put("telephone","5579544");
        values.put("status",2);
        db.insert("User",null,values);
        values.clear();
        //课程数据
        values.put("teacherID",2);
        values.put("managerID",3);
        values.put("name","数值分析");
        db.insert("Class",null,values);
        values.clear();
        values.put("teacherID",2);
        values.put("managerID",3);
        values.put("name","软件工程");
        db.insert("Class",null,values);
        values.clear();
        //课程数据
        values.put("teacherID",4);
        values.put("managerID",3);
        values.put("name","英语");
        db.insert("Class",null,values);
        values.clear();

        values.put("teacherID",2);
        values.put("managerID",3);
        values.put("name","操作系统");
        db.insert("Class",null,values);
        values.clear();

        values.put("teacherID",4);
        values.put("managerID",3);
        values.put("name","数学建模");
        db.insert("Class",null,values);
        values.clear();
        Log.d("DatabaseCreator","add data success");
    }


}
