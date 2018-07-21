package com.example.starxin.fitness.domain;

import cn.bmob.v3.BmobObject;

/**
 * Created by StarXin on 2018/5/5.
 */

public class Users extends BmobObject {
    private String name;//用户名
    private String password;//密码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
