package com.leon.ssm.service;

import com.leon.ssm.entity.Factory;
import com.leon.ssm.mapper.FactoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;

/*
写在类上，默认所有的方法都应用声明式事务
*/

@Service
@Transactional(rollbackFor = Exception.class)
public class FactoryService {

    @Resource
    private FactoryMapper factoryMapper;


    /** 使用 @Transactional 描述事务
     * 当方法执行成功时自动提交，抛出 RuntimeException 及其子类时自动回滚
     * @Transactional 配置rollbackFor 参数可以指定针对某个异常类进行回滚
     * 利用 rollbackFor=Exception.class 遇到所有异常都会回滚
     */
    @Transactional(rollbackFor = Exception.class)
    public void initFactory() throws ParseException {
        for (int i = 0; i < 5; i++) {
            if(i==3){
                throw new ParseException("", 1);
//                throw new RuntimeException("Test for SQL transaction failed!");
            }
            Factory factory = new Factory();

            factory.setName("事务工厂："+(i+1));
            factory.setAdCode("440111");
            factory.setDeleted(false);
            factory.setEnabled(true);
            factory.setLongitude(113.296464f);
            factory.setLatitude(23.348045f);

            factoryMapper.insert(factory);
        }
    }

    // 指定不使用声明式事务，且方法优先级比类定义的声明式事务级别高
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Factory findById(Integer fabId){

        return factoryMapper.findById(fabId);
    }

}
