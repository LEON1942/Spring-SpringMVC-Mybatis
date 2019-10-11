package com.springboot;

import com.springboot.dao.IUserDao;
import com.springboot.service.HrService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        IUserDao userDao = (IUserDao) context.getBean("udao");
        userDao.create();
        HrService hrService = (HrService) context.getBean("hrService");
        hrService.entry();

    }
}
