package com.springmvc.controller;


import com.springmvc.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

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

}
