package com.leon.ssm.controller;

import com.leon.ssm.entity.Factory;
import com.leon.ssm.mapper.FactoryMapper;
import com.leon.ssm.service.FactoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public ModelAndView showHello(){
        ModelAndView mav = new ModelAndView("hello");
        mav.addObject("title", "Hello SSM!");
        return mav;
    }

    @Resource
    private FactoryMapper factoryMapper;

    @Resource
    private FactoryService factoryService;

    @GetMapping("/factory")
    public ModelAndView getFactory(){
        ModelAndView mav  = new ModelAndView("factory_detail");
        Factory factory = factoryMapper.findById(1);
        mav.addObject("factory", factory);

        Map params = new HashMap();
        params.put("minId", 3);
        params.put("enabled", true);

//        List<Factory> factories = factoryMapper.findByCondition(params);
        List<Factory> factories = factoryMapper.findByCondition(5, true);

        mav.addObject("factories", factories);

        return mav;
    }

    @GetMapping("/factory/init")
    @ResponseBody
    public String initFactory(){
        factoryService.initFactory();
        return "Success";
    }

}
