package com.springboot.dao;

import org.springframework.stereotype.Repository;


@Repository("udao")
public class UserDaoExt implements IUserDao{

    public UserDaoExt() {
        System.out.println("初始化用户（升级版）。。。。"+this);
    }

    public void create() {
        System.out.println("创建用户成功（升级版）。"+this);
    }
}
