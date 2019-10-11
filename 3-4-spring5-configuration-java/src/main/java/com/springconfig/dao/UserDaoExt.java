package com.springconfig.dao;

public class UserDaoExt implements IUserDao {
    public void create() {
        System.out.println("创建员工信息（升级版）"+this);
    }
}
