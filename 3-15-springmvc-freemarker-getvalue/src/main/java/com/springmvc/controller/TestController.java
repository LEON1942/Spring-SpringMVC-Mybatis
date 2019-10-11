package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody // 将return返回的字符串直接输出到客户端，而不是跳转页面
    public String test(){

        return "Success!";
    }

    @GetMapping("/test/freemaker")
    public ModelAndView showFreemarkerTestor(){
       ModelAndView mav = new ModelAndView("/test");
       mav.addObject("testString", "测试中文");
       return mav;
    }
}
