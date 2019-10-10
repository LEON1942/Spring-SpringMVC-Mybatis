package com.springmvc.controller;


import com.springmvc.dao.User;
import com.springmvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/url")  // 为 Controller 中所有的方法指定url前缀
public class UrlController {

    @Resource
    UserService userService;

    @GetMapping("/get")
    @ResponseBody
//    @RequestMapping("/get")  // 通用请求，同时支持GET，POST，实际开发中并不推荐使用
    public String doGet(){

        return "doGet";
    }


    @PostMapping("/post")
    @ResponseBody
    /**
     * 1、参数接收数据适合场景：小数据的快速处理
     * 2、使用实体（Java Bean）进行数据接收，适合机构化数据，适合大数据量的接收,必须保证保单项名称与Bean的属性名一致
     * 3、路径变量是在Restful开发风格中最常用的做法
     */
    public String doPost(String username, @RequestParam("password") String password, String birthday, User user){

        return "doPost "+ username + " " + password + " " + birthday;
    }

    @GetMapping("/artical/{aid}.html") // 路径变量
    @ResponseBody
    public String artical(@PathVariable("aid") Integer artId){

        return String.valueOf(artId);
    }

    @GetMapping("/reg")
    @ResponseBody
    public User registe(){
        User user = userService.registe();
        return user;
    }

    @GetMapping("/reg/success")
    public ModelAndView registe_success(){
        ModelAndView mav = new ModelAndView("/registe_success.jsp");
        User user = userService.registe();
        mav.addObject("user", user);
        return mav;
    }

}
