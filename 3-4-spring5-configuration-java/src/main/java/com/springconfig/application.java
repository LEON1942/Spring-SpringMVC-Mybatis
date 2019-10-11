package com.springconfig;

import com.springconfig.dao.IUserDao;
import com.springconfig.service.HrService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class application {
    public static void main(String[] args) {
        //  初始化 spring ioc 容器， 同时根据xml创建对象
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        IUserDao udao = (IUserDao) ctx.getBean("udao");
        udao.create();
        HrService hrService = (HrService) ctx.getBean("hservice");
        hrService.entry();
    }
}
