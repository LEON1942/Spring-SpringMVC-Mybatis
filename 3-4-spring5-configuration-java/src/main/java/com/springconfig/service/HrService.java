package com.springconfig.service;

import com.springconfig.dao.IUserDao;
import com.springconfig.dao.UserDao;

public class HrService {

    public HrService(){
        System.out.println("正在初始化。。。"+this);
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
        System.out.println("通过setUserDao（）方法赋值给userDao"+this.userDao);
    }

    IUserDao userDao = null;
    public void entry(){
        userDao.create();
    }
}
