package com.springmvc.service;

import com.springmvc.dao.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    public User registe(){
        User user = new User();
        user.setUsername("LEON");
        user.setPassword("abc");
        user.setBirthday(new Date());
        return user;
    }

}
