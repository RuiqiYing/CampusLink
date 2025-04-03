package com.zjgsu.kjr.campus_social_platform.sign_in_up;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

public class PersonData extends BmobObject {
    String ID;
    private String name;
    private String college;
    private String password;
    private String class1;
    private String phone;
    private String passwordsure;
    private String contact;
    private String introduce;
    private BmobFile picture;

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordsure(String passwordsure) {
        this.passwordsure = passwordsure;
    }

    public String getClass1() {
        return class1;
    }

    public String getCollege() {
        return college;
    }

    public String getContact() {
        return contact;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordsure() {
        return passwordsure;
    }

    public String getPhone() {
        return phone;
    }

    public String getIntroduce() {
        return introduce;
    }


    @Override
    public String getObjectId() {
        return super.getObjectId();
    }

    public BmobFile getPicture() {
        return picture;
    }

}


