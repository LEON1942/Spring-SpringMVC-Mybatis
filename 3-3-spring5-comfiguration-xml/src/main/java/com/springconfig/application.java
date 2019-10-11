package com.springconfig;

import com.springconfig.dao.IUserDao;
import com.springconfig.service.HrService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class application {
    public static void main(String[] args) {
        //  初始化 spring ioc 容器， 同时根据xml创建对象
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        IUserDao udao = (IUserDao) ctx.getBean("udao");
        udao.create();
        HrService hrService = (HrService) ctx.getBean("hrservice");
        hrService.entry();
    }
}
