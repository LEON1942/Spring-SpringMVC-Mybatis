# Spring 与 Mybatis 基本整合案例 - 声明式事务

## 1、在 applicationContext.xml 中开启注解式声明式事务
①添加事务配置项相关的约束
    
    <beans  ...
        xmlns:tx="http://www.springframework.org/schema/tx"
        ...
        xsi:schemaLocation=" ... 
            http://www.springframework.org/schema/tx
            http://www.springframework.org/schema/tx/spring-tx.xsd
            ...
            >
    </beans>
  
②配置事务相关bean
    
    <!--事务管理器 TransactionManger 提供了声明式事务的支持，在程序成功提交，运行时异常回滚-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--开始注解式的声明式事务-->
    <tx:annotation-driven transaction-manager="transactionManager" />

## 2、利用 @Transactional 描述事务
①在业务类的方法上单独使用事务

    /** 使用 @Transactional 描述事务
     * 当方法执行成功时自动提交，抛出 RuntimeException 及其子类时自动回滚
     */
    @Transactional
    public void initFactory(){
        for (int i = 0; i < 5; i++) {
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

②在业务类添加 @Transactional，则该类中所有的方法都默认开启声明式事务

    @Service
    @Transactional
    public class FactoryService {
        ...
    }

## 3、设置事务传播方式为 Propagation.NOT_SUPPORTED 不使用事务

    // 指定不使用声明式事务，且方法优先级比类定义的声明式事务级别高
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Factory findById(Integer fabId){
        return factoryMapper.findById(fabId);
    }

## 4、@Transactional rollbackFor 指定回滚的异常类
默认情况下，@Transactional 仅针对 RuntimeException 及其 子类 进行回滚操作，
类似其他异常如 ParseException 等抛出时，异常发生前的db操作仍然会提交到数据库，
所以，可以通过配置rollbackFor指定异常类进行回滚操作。

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
                // throw new RuntimeException("Test for SQL transaction failed!");
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
    
Exception 类是其他异常的父类, 这样可以针对程序中所有的异常进行回滚。
   
    




