package com.springmvc.dao;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User {

    private String username;
    private String password;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date birthday;

    public User(String username, String password, Date birthday) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
