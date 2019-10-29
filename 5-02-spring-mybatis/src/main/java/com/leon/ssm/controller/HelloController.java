package com.leon.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public ModelAndView showHello(){
        ModelAndView mav = new ModelAndView("hello");
        mav.addObject("title", "Hello SSM!");
        return mav;
    }
}
