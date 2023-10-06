package com.example.myapplication.model;

import com.example.myapplication.R;

public class Apply {
    private int id;
    private int userID;
    private int classID;
    private String reason;
    private int state;
    private String backReason;
    private int confirm;

    public Apply(int id, int userID, int classID, String reason, int state, String backReason, int confirm, String image) {
        this.id = id;
        this.userID = userID;
        this.classID = classID;
        this.reason = reason;
        this.state = state;
        this.backReason = backReason;
        this.confirm = confirm;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }



    public int imageId(){
        if(state == 5){return R.drawable.fail;}
        else if(state == 4){return R.drawable.complete;}
        else {return R.drawable.wait;}
    }
    public String getStringState(){
        switch (state){
            case 1:return "申请已提交";
            case 2:return "课程主讲教师审批中";
            case 3:return "课程主管教师审批中";
            case 4:return "审批成功";
            case 5:return "申请驳回";
            default:return null;
        }

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }


}
