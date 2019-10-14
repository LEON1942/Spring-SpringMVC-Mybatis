package com.springmvc.controller;

import com.springmvc.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody // 将return返回的字符串直接输出到客户端，而不是跳转页面
    public String test(){

        return "Success!";
    }

    @GetMapping("/test/freemarker")
    public ModelAndView showFreemarkerTestor(){
       ModelAndView mav = new ModelAndView("/test");
       mav.addObject("testString", "测试中文");
       return mav;
    }

    @GetMapping("/test/fm/function")
    public ModelAndView fmFunctions(){
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("LEON 0"+(i+1));
            user.setBirthday(new Date());
            user.setPassword("pwd"+(i+1));
            user.setSalary(3500.34f*(i+1));
            user.setAge(15*(5-i));
            users.add(user);
        }

        ModelAndView mav = new ModelAndView("/functions");
        mav.addObject("author", "leon1942");
        mav.addObject("users", users);
        return mav;
    }
}
