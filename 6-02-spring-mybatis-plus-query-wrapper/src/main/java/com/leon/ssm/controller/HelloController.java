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
import java.util.Date;
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

        for (ParentOrder order : pos) {

            System.out.println(order.getOrderNo() + " "+ order.getReceiverAddress());
        }

        ParentOrder parent_order = pom.selectById("0001a6cd12a8b39f68ebd7e7fbc718de");

        System.out.println(parent_order.getOrderNo());

        return "got one!";
    }

    @GetMapping("/parent/order/get/by/order_no")
    @ResponseBody
    public ParentOrder get_by_order_no(){
        QueryWrapper<ParentOrder> qw = new QueryWrapper<ParentOrder>();
        qw.eq("order_no", "PR0SKQQTM020190511103408CZK7WE9C");
        ParentOrder poder = pom.selectOne(qw);

        QueryWrapper<ParentOrder> qw2 = new QueryWrapper<>();
        qw2.between("total_price", 100, 2000);

        List<ParentOrder> orders = pom.selectList(qw2);

        for(ParentOrder od : orders){
            System.out.println(od.getOrderNo()+" "+od.getReceiver()+"-"+od.getReceiverAddress()+";"+od.getTotalPrice());
        }

        QueryWrapper<ParentOrder> qw3 = new QueryWrapper<>();
        qw3.like("receiver_address", "广州");
        qw3.last("limit 1");

        ParentOrder orders2 = pom.selectOne(qw3);
        ParentOrder od = orders2;
//        for(ParentOrder od : orders2){
        System.out.println("like --- >"+od.getOrderNo()+" "+od.getReceiver()+"-"+od.getReceiverAddress()+";"+od.getTotalPrice());
//        }

        return poder;
    }

}
