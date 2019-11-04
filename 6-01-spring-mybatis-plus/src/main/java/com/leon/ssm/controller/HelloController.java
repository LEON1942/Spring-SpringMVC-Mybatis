package com.leon.ssm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leon.ssm.entity.Factory;
import com.leon.ssm.entity.ParentOrder;
import com.leon.ssm.mapper.FactoryMapper;
import com.leon.ssm.mapper.ParentOrderMapper;
import com.leon.ssm.service.FactoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
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
    public String initFactory() throws ParseException {
        factoryService.initFactory();
        return "Success";
    }

    @Resource
    private ParentOrderMapper pom;
    @GetMapping("/parent/order/get")
    @ResponseBody
    public String get_parent_order(){

        List<ParentOrder> pos = pom.selectList(new QueryWrapper<ParentOrder>().eq("order_no", "PR0SKQQTM020190511103408CZK7WE9C"));

        pos.forEach(System.out::println);

        ParentOrder parent_order = pom.selectById("0001a6cd12a8b39f68ebd7e7fbc718de");

        System.out.println(parent_order.getOrderNo());

        return "got one!";
    }

}
