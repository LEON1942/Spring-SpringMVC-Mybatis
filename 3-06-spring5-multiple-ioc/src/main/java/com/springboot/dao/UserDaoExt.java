package com.springboot.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/*
*
* 在Bean 上添加 Scope 注解可以设置Bean的 多例模式
* Scope 包含5个选项：
* singleton：单例模式（默认）| 适用无状态的Bean对象
* prototype：多例模式（获getBean()时创建）| 适用有状态的Bean对象
* request：在request生命周期中单例
* session：在session生命周期中单例
* global session：类似session，仅仅在基于portlet的web应用中才有意义
*
* */
@Repository("udao")
@Scope("prototype")
public class UserDaoExt implements IUserDao{

    public UserDaoExt() {
        System.out.println("初始化用户（升级版）。。。。"+this);
    }

    public void create() {
        System.out.println("创建用户成功（升级版）。"+this);
    }
}
