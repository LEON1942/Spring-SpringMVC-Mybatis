package com.springboot.service;

import com.springboot.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// beanid 自动设置为类名首字母小写
@Service
public class HrService {
    /*
    * 1、设置 name 则根据 beanid 进行加载
    * 2、不设置 name，则优先根据属性名进行加载
    * 3、如果 beanid 不存在，则根据类型进行记载（不推荐使用，bean类型非唯一）
    * */
//    @Resource
//    @Autowired(不推荐)
    @Resource(name="udao")
    IUserDao userDao = null;

    public HrService() {
        System.out.println("正在创建Hr..."+this);
    }

    public void entry(){
        System.out.println("员工入职操作。。");
        this.userDao.create();
    }

}
