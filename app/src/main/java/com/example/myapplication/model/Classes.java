package com.example.myapplication.model;

public class Classes {
    private int id;
    private int teacherID;
    private int managerID;
    private String name;

    public Classes(int id, int teacherID, int managerID, String name) {
        this.id = id;
        this.teacherID = teacherID;
        this.managerID = managerID;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
