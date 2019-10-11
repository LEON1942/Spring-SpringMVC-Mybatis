package com.springboot.dao;

import org.springframework.stereotype.Repository;


//@Repository("udao")
public class UserDao implements IUserDao{

    public UserDao() {
        System.out.println("初始化。。。。"+this);
    }

    public void create() {
        System.out.println("创建成功。"+this);
    }
}
