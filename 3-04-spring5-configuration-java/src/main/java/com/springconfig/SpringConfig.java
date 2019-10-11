package com.springconfig;

import com.springconfig.dao.IUserDao;
import com.springconfig.dao.UserDao;
import com.springconfig.dao.UserDaoExt;
import com.springconfig.service.HrService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//替代XML，成为IOC容器的主要配置来源
@Configuration
public class SpringConfig {


    /*
    * @Bean用于初始化对象
    * 默认方法名是beanid
    * 在方法的内部手动进行实例化操作，并将对象return返回
    * */

    @Bean
    public IUserDao udao(){
        return new UserDaoExt();
    }

    @Bean(name = "hservice")
    public HrService hrService(){
        HrService hrService = new HrService();
        hrService.setUserDao(this.udao());
        return hrService;
    }
}
