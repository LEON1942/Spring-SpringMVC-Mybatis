package com.springmvc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/url")  // 为 Controller 中所有的方法指定url前缀
public class UrlController {

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
     * 2、使用实体（Java Bean）进行数据接收，适合机构化数据，适合大数据量的接收
     * 3、路径变量实在Restful开发风格中最常用的做法
     */
    public String doPost(String username, String password, String birthday){

        return "doPost "+ username + " " + password + " " + birthday;
    }

}
