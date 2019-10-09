package com.springmvc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/url")  // 为 Controller 中所有的方法指定url前缀
public class UrlController {

    @GetMapping("/get")
    @ResponseBody
    @RequestMapping("/get")  // 通用请求，同时支持GET，POST，实际开发中并不推荐使用
    public String doGet(){

        return "doGet";
    }


    @PostMapping("/post")
    @ResponseBody
    public String doPost(){

        return "doPost";
    }

}
