package com.example.myapplication.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.model.Apply;
import com.example.myapplication.model.Classes;
import com.example.myapplication.model.Remember;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    public void test(String wAccount,String wPsw,DatabaseCreator databaseCreator){
        Log.d("DatabaseCreator",wAccount);
        Log.d("DatabaseCreator",wPsw);
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select id,password,status from User where account=?",new String[]{"77"});
        //Cursor cursor = db.query("User",new String[]{"password","account"},"account = ?",new String[]{"77"},null,null,null);
        if(cursor.moveToFirst()){
            do {
                int a = cursor.getColumnIndex("password");
                String psw = cursor.getString(a);
                Log.d("DatabaseCreator",psw);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }
    public User loginQuery(String wAccount, String wPsw,DatabaseCreator databaseCreator){
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select id,password,status from User where account=?",new String[]{wAccount});
        if(cursor.moveToFirst()){
            do{
                int a = cursor.getColumnIndex("password");
                String psw = cursor.getString(a);
                if(wPsw.equals(psw)){
                    a = cursor.getColumnIndex("status");
                    int status = cursor.getInt(a);
                    a =cursor.getColumnIndex("id");
                    int id = cursor.getInt(a);
                    return new User(id,null,null,null,-1,null,status);
                }
            }while (cursor.moveToNext());
            cursor.close();
        }
        return new User(-1,null,null,null,-1,null,-1 );
    }
    public List<Apply> findAllApplyByUserId(int userId, DatabaseCreator databaseCreator){
            SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Apply where userID=?",new String[]{userId+""});
            List<Apply> applyList = new ArrayList<>();
            if(cursor.moveToFirst()){
                do{
                    int m_id  = cursor.getColumnIndex("id");
                    int m_userID  = cursor.getColumnIndex("userID");
                    int m_classID  = cursor.getColumnIndex("classID");
                    int m_reason  = cursor.getColumnIndex("reason");
                    int m_state  = cursor.getColumnIndex("state");
                    int m_backReason  = cursor.getColumnIndex("backReason");
                    int m_confirm = cursor.getColumnIndex("confirm");
                    int m_image = cursor.getColumnIndex("image");
                    Apply apply = new Apply(cursor.getInt(m_id),cursor.getInt(m_userID),cursor.getInt(m_classID),cursor.getString(m_reason),cursor.getInt(m_state),cursor.getString(m_backReason),cursor.getInt(m_confirm),cursor.getString(m_image));
                    applyList.add(apply);
                }while (cursor.moveToNext());

            }
            return applyList;
        }
    public User findUserById(int userId, DatabaseCreator databaseCreator) {
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from User where id=?",new String[]{userId+""});
        if(cursor.moveToFirst()){
                int m_id  = cursor.getColumnIndex("id");
                int m_account  = cursor.getColumnIndex("account");
                int m_password  = cursor.getColumnIndex("password");
                int m_name  = cursor.getColumnIndex("name");
                int m_sex  = cursor.getColumnIndex("sex");
                int m_telephone  = cursor.getColumnIndex("telephone");
                int m_status  = cursor.getColumnIndex("status");
                User user = new User(cursor.getInt(m_id),cursor.getString(m_account),cursor.getString(m_password),cursor.getString(m_name),cursor.getInt(m_sex),cursor.getString(m_telephone),cursor.getInt(m_status));
                cursor.close();
                return user;
        }
        return new User(-1,null,null,null,-1,null,-1 );
    }
    public void changeUserInfoById(ContentValues values, int userId, DatabaseCreator databaseCreator) {
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        db.update("User",values,"id = ?",new String[]{userId+""});
    }
    public Classes findClassById(int classId, DatabaseCreator databaseCreator) {
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Class where id=?",new String[]{classId+""});
        if(cursor.moveToFirst()){
            int m_id  = cursor.getColumnIndex("id");
            int m_teacherID  = cursor.getColumnIndex("teacherID");
            int m_managerID  = cursor.getColumnIndex("managerID");
            int m_name  = cursor.getColumnIndex("name");
            Classes classes = new Classes(cursor.getInt(m_id),cursor.getInt(m_teacherID),cursor.getInt(m_managerID),cursor.getString(m_name));
            cursor.close();
            return classes;
        }
        return new Classes(-1,-1,-1,null);
    }
    public Apply findApplyById(int applyId, DatabaseCreator databaseCreator) {
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Apply where id=?",new String[]{applyId+""});
        if(cursor.moveToFirst()){
            int m_id  = cursor.getColumnIndex("id");
            int m_userID  = cursor.getColumnIndex("userID");
            int m_classID  = cursor.getColumnIndex("classID");
            int m_reason  = cursor.getColumnIndex("reason");
            int m_state  = cursor.getColumnIndex("state");
            int m_backReason  = cursor.getColumnIndex("backReason");
            int m_confirm = cursor.getColumnIndex("confirm");
            int m_image = cursor.getColumnIndex("image");
            Apply apply = new Apply(cursor.getInt(m_id),cursor.getInt(m_userID),cursor.getInt(m_classID),cursor.getString(m_reason),cursor.getInt(m_state),cursor.getString(m_backReason),cursor.getInt(m_confirm),cursor.getString(m_image));
            cursor.close();
            return apply;
        }
        return new Apply(-1,-1,-1,null,-1,null,-1,null);
    }
    //教师查询功能
    public List<Apply> findApplyOfTc(DatabaseCreator databaseCreator, String string){
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Apply where classID=?",new String[]{string});
        List<Apply> applyList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                int m_id  = cursor.getColumnIndex("id");
                int m_userID  = cursor.getColumnIndex("userID");
                int m_classID  = cursor.getColumnIndex("classID");
                int m_reason  = cursor.getColumnIndex("reason");
                int m_state  = cursor.getColumnIndex("state");
                int m_backReason  = cursor.getColumnIndex("backReason");
                int m_confirm = cursor.getColumnIndex("confirm");
                int m_image = cursor.getColumnIndex("image");
                Apply apply = new Apply(cursor.getInt(m_id),cursor.getInt(m_userID),cursor.getInt(m_classID),cursor.getString(m_reason),cursor.getInt(m_state),cursor.getString(m_backReason),cursor.getInt(m_confirm),cursor.getString(m_image));
                applyList.add(apply);
            }while (cursor.moveToNext());
        }
        db.close();
        return applyList;
    }
    //教师授课/主管教师负责课程(与申请state无关)
    public List<Apply> findApplyListByIdOfTc(int userId, DatabaseCreator databaseCreator, int status){
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        Cursor cursor;
        if(status == 2){
             cursor = db.rawQuery("select * from Class where teacherID=?",new String[]{userId+""});
             Log.d("DataProcessor","first query done");
        }
        else if(status == 3){
             cursor = db.rawQuery("select * from Class where managerID=?",new String[]{userId+""});
        }else {return null;}
        List<String> classesId = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                int m_id  = cursor.getColumnIndex("id");
                classesId.add(cursor.getInt(m_id)+"");
            }while (cursor.moveToNext());
        }
        cursor.close();
        List<Apply> applyList = new ArrayList<>();
        String[] strings = new String[classesId.size()];
        for (int i = 0; i < strings.length; i++) {
            List<Apply> newApply = findApplyOfTc(databaseCreator,classesId.get(i));
            for (int j = 0; j < newApply.size(); j++) {
                applyList.add(newApply.get(j));
            }
        }
        db.close();
        return applyList;
    }
    //所有课程数据
    public List<Classes> getAllClasses(DatabaseCreator databaseCreator,int userid){
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        List<Classes> classesList = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select * from Class",new String[]{});
        if(cursor.moveToFirst()){
            do {
                int m_id  = cursor.getColumnIndex("id");
                int m_teacherID  = cursor.getColumnIndex("teacherID");
                int m_managerID  = cursor.getColumnIndex("managerID");
                int m_name  = cursor.getColumnIndex("name");
                Classes classes = new Classes(cursor.getInt(m_id),cursor.getInt(m_teacherID),cursor.getInt(m_managerID),cursor.getString(m_name));
                classesList.add(classes);
            }while (cursor.moveToNext());
        }
        //去除存在申请的课程
        List<Apply> applies  = findAllApplyByUserId(userid,databaseCreator);
        for (int i = applies.size()-1; i > -1; i--) {
            int state = applies.get(i).getState();
            if(state!= 4 && state!= 5){  //该学生对该课程存在未结束的申请
                Classes classes = findClassById(applies.get(i).getClassID(),databaseCreator);
              //  String name = classes.getName();
                for (int j = 0; j < classesList.size(); j++) {
                    if (classes.getId() == classesList.get(j).getId()){
                        classesList.remove(j);
                    }
                }
            }
        }
        db.close();
        return classesList;
    }
    //申请功能
    public boolean addApply(ContentValues values,DatabaseCreator databaseCreator){
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        db.insert("Apply",null,values);
        return true;
    }
    //审批功能(与申请状态有关)
    public List<Apply> getReadList(DatabaseCreator databaseCreator,int userId){
        int status = findUserById(userId,databaseCreator).getStatus();
        List<Apply> applyList = findApplyListByIdOfTc(userId,databaseCreator,status);
        List<Apply> data = new ArrayList<>();
        Apply apply;
        if(status == 2){
            for (int i = 0; i < applyList.size(); i++) {
                if(applyList.get(i).getState() == 1){
                    apply = applyList.get(i);
                    apply.setState(2);
                    data.add(apply);
                    ContentValues values = new ContentValues();
                    values.put("state",2);
                    values.put("confirm",0);
                    updateAppleData(databaseCreator,values,apply.getId());
                }
                else if(applyList.get(i).getState() == 2){   data.add(applyList.get(i));}
                else {
                }
            }
        }else {  //主管教师
            for (int i = 0; i < applyList.size(); i++) {
                if(applyList.get(i).getState() == 3){
                    data.add(applyList.get(i));
                }
                else {
                }
            }
        }
        return data;
        }
    //查找具体申请
    public List<Apply> queryApply(String query,String condition,DatabaseCreator databaseCreator,List<Apply> applyList,int id){
           SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
           Cursor cursor;
           List<Apply> target = new ArrayList<>();
           switch (condition){
               case "按课程名称":{
                   List<Integer> list = new ArrayList<>();
                   cursor= db.rawQuery("select id from Class where name like ?",new String[]{"%"+query+"%"});
                   if(cursor.moveToNext()){
                       do {
                           int m_id  = cursor.getColumnIndex("id");
                           list.add(cursor.getInt(m_id));
                       }while (cursor.moveToNext());
                   }
                   for (int i = 0; i < applyList.size(); i++) {  //classID list
                       for (int j = 0; j < list.size(); j++) {
                           int classId = applyList.get(i).getClassID();
                           if( classId == list.get(j)){
                               target.add(applyList.get(i));
                           }
                       }
                   }
                   break;
               }
               case "按教师名称":{  //学生有的查询条件
                   List<Integer> list = new ArrayList<>();  //teacherId list
                   cursor= db.rawQuery("select id from User where name like ?",new String[]{"%"+query+"%"});
                   if(cursor.moveToNext()){
                       do {
                           int m_id  = cursor.getColumnIndex("id");
                           list.add(cursor.getInt(m_id));
                       }while (cursor.moveToNext());
                   }
                   cursor.close();
                   for (int i = 0; i < list.size(); i++) {  //教师id
                       merge(target,findApplyListByIdOfTc(list.get(i),databaseCreator,2));
                   }
                   //比较去除不是该学生
                   for (int i = 0; i < target.size(); i++) {
                       if(id != target.get(i).getUserID()){
                           target.remove(i);
                       }
                   }
                   break;
               }
               case "按主管教师名称":  //学生有的查询条件
               {
                   List<Integer> list = new ArrayList<>();
                   cursor= db.rawQuery("select id from User where name like ?",new String[]{"%"+query+"%"});
                   if(cursor.moveToNext()){
                       do {
                           int m_id  = cursor.getColumnIndex("id");
                           list.add(cursor.getInt(m_id));
                       }while (cursor.moveToNext());
                   }
                   cursor.close();
                   for (int i = 0; i < list.size(); i++) {  //教师id
                       merge(target,findApplyListByIdOfTc(list.get(i),databaseCreator,3));
                   }
                   for (int i = 0; i < target.size(); i++) {
                       if(id != target.get(i).getUserID()){
                           target.remove(i);
                       }
                   }
                   break;
               }
               case "按审批状态":{
                   int state = switchStateToInt(query);
                   for (int i = 0; i < applyList.size(); i++) {
                       if(state == applyList.get(i).getState()){target.add(applyList.get(i));}
                   }
                   break;
               }
               default:break;
           }
           return target;
       }

    private void merge(List<Apply> a,List<Apply> b) {
        for (int i = 0; i < b.size(); i++) {
            a.add(b.get(i));
        }
    }

    public List<Apply> getApplyListByClassID(DatabaseCreator databaseCreator, String s){
             SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
             Cursor cursor = db.rawQuery("select * from Apply where classID = ?",new String[]{s});
        List<Apply> applyList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                int m_id  = cursor.getColumnIndex("id");
                int m_userID  = cursor.getColumnIndex("userID");
                int m_classID  = cursor.getColumnIndex("classID");
                int m_reason  = cursor.getColumnIndex("reason");
                int m_state  = cursor.getColumnIndex("state");
                int m_backReason  = cursor.getColumnIndex("backReason");
                int m_confirm = cursor.getColumnIndex("confirm");
                int m_image = cursor.getColumnIndex("image");
                Apply apply = new Apply(cursor.getInt(m_id),cursor.getInt(m_userID),cursor.getInt(m_classID),cursor.getString(m_reason),cursor.getInt(m_state),cursor.getString(m_backReason),cursor.getInt(m_confirm),cursor.getString(m_image));
                applyList.add(apply);
            }while (cursor.moveToNext());
        }
        return applyList;
       }
    public int switchStateToInt(String state){
        switch (state){
            case "申请已提交":return 1;
            case "课程主讲教师审批中":return 2;
            case "课程主管教师审批中":return 3;
            case "审批成功":return 4;
            case "申请驳回":return 5;
            default:return -1;
        }
    }
    //学生确认申请进度
    public void updateAppleData(DatabaseCreator databaseCreator,ContentValues values,int id){
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        db.update("Apply",values,"id = ?",new String[]{id+""});
        db.close();
    }
    //通知
    public List<Apply>  getInfoList(DatabaseCreator databaseCreator,int userid,int status){
        List<Apply> applyList;
        if(status == 1){
            applyList =  findAllApplyByUserId(userid,databaseCreator);
            for (int i = applyList.size()-1; i > -1; i--) {
                if(applyList.get(i).getConfirm() != 0){applyList.remove(i);}
            }
        }else if(status == 2){
             applyList = findApplyListByIdOfTc(userid,databaseCreator,status);
            for (int i = applyList.size()-1; i > -1; i--)  {
                if(applyList.get(i).getState() != 1 ){
                    applyList.remove(i);
                }
            }
        }
        else { // 3
            applyList = findApplyListByIdOfTc(userid,databaseCreator,status);
            for (int i = applyList.size()-1; i > -1; i--){
                if(applyList.get(i).getState() != 2 ){
                    applyList.remove(i);
                }
            }
        }
        return applyList;
    }
    public void rememberAccount(DatabaseCreator databaseCreator,ContentValues values){
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        List<Remember> remembers = getRmAccount(databaseCreator);
        int id = (Integer) values.get("userID");
        for (int i = 0; i < remembers.size(); i++) {
            if (remembers.get(i).getUserId() == id){
                return;
            }
        }
        db.insert("Remember",null,values);
    }
    public List<Remember> getRmAccount(DatabaseCreator databaseCreator){
        List<Remember> remembers = new ArrayList<>();
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Remember",null);
        if(cursor.moveToFirst()){
            do{
                int m_id  = cursor.getColumnIndex("id");
                int m_userID  = cursor.getColumnIndex("userID");
                Remember remember = new Remember(cursor.getInt(m_id),cursor.getInt(m_userID));
                remembers.add(remember);
            }while (cursor.moveToNext());
        }
        return remembers;
    }
    public void  deleteRemember(DatabaseCreator databaseCreator,Remember remember){
        SQLiteDatabase db = databaseCreator.getDbHelper().getWritableDatabase();
        db.delete("Remember","id=?",new String[]{remember.getId()+""});
    }
}
