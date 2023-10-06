package com.example.myapplication.model;

import android.database.Cursor;

public class User {
    private int id;
    private String account;
    private String password;
    private String name;
    private int sex;
    private String telephone;
    private int status;


    public User(int id, String account, String password, String name, int sex, String telephone, int status) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.telephone = telephone;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
