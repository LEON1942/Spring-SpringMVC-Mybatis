package com.springmvc.controller;


import com.springmvc.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class FreeMarkerController {

    @GetMapping("/fm/info")
    public ModelAndView showInfo(){
        User user = new User();

        user.setUsername("LEON");
        user.setName("老姚");
        user.setAge(29);
        user.setPassword("123");
        user.setSalary(10000f);
        user.setBirthday(new Date());

        ModelAndView mav = new ModelAndView("info");
        mav.addObject(user);
        mav.addObject("title", "用户信息");
        return mav;
    }


    @GetMapping("/fm/list")
    public ModelAndView showList(){
        ModelAndView mav = new ModelAndView("list");
        List<User> users = new LinkedList<User>();
        for(int i=0; i<5;i++) {
            User user = new User();

            user.setUsername("学生" + (i + 1));
            user.setName("小" + i);
            user.setAge(i * 8);
            user.setPassword("123");
            user.setSalary(3000f * i);
            user.setBirthday(new Date());
            users.add(user);
        }
        mav.addObject("users", users);
        return mav;
    }

}
