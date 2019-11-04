package com.leon.ssm;


import com.leon.ssm.entity.Factory;
import com.leon.ssm.service.FactoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.ParseException;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FactoryServiceTest {

    @Resource
    private FactoryService factoryService;

    /**
     * @Transactional 默认在测试用例执行成功后，为了保证原始数据不被测试数据污染
     * 自动执行回滚操作，要解决这个问题需要设置 @Rollback(false)关闭自动回滚
     */
    @Test
    @Transactional
//    @Rollback(false)
    public void initFactory() throws ParseException {
        factoryService.initFactory();

    }

    /**
     * 程序运行成功 绿色对勾
     * 程序运行失败 红色叹号
     * 程序运行逻辑错误 黄色叉号
     */
    @Test
    public void findById(){
        Factory factory = factoryService.findById(1);
        // Assert 断言，对程序结果进行判断
        assertTrue("未找到Factory对象", factory != null);

    }

}
