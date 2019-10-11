package com.springconfig.dao;

public class UserDao implements IUserDao {

    public UserDao(){
        System.out.println("初始化用户实体类"+this);
    }

    public void create() {
        System.out.println("用户创建成功！");
    }
}
