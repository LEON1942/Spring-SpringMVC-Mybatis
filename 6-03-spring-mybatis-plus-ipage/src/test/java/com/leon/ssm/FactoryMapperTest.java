package com.leon.ssm;

import com.leon.ssm.entity.Factory;
import com.leon.ssm.mapper.FactoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * SpringJUnit4ClassRunner.class 是 spring-test 提供的整合类
 * Junit 将单元测试管理权交给 Spring，便可以在测试用例启动时
 * 自动加载 applicationContext.xml，并初始化 IOC 容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FactoryMapperTest {

    @Resource
    private FactoryMapper factoryMapper;

    // @Test 说明下面方法是可以独立执行的单元测试方法
    @Test
    public void testFindById(){

        Factory factory = factoryMapper.findById(1);
        System.out.println(factory.getName());

    }


}
