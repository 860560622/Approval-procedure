package com.example.myapplication.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.myapplication.controller.DatabaseCreator;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_REMEMBER = "Create table Remember("
            +"id integer primary key autoincrement,"
            +"userID integer)";
    public static final String CREATE_APPLY = "Create table Apply("
            +"id integer primary key autoincrement,"
            +"userID integer,"
            +"classID integer,"
            +"reason text,"
            +"state integer,"
            +"backReason text,"
            +"confirm integer,"
            +"image text)";
    public static final String CREATE_USER = "Create table User("
            +"id integer primary key autoincrement,"
            +"account text,"
            +"password text,"
            +"name text,"
            +"sex integer,"
            +"telephone text,"
            +"status integer)";
    public static final String CREATE_CLASS = "Create table Class("
            +"id integer primary key autoincrement,"
            +"teacherID integer,"
            +"managerID integer,"
            +"name text)";
    private Context mContext;
    public MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_APPLY);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_CLASS);
        db.execSQL(CREATE_REMEMBER);
        Toast.makeText(mContext, "Create success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Apply");
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Class");
        db.execSQL("drop table if exists Remember");
        onCreate(db);
    }
}
