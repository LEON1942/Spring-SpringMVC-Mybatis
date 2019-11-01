# Spring 与 Junit 整合

## 1、在 pom.xml 中加载 Junit 相关依赖项

    <!--Junit 单元测试框架-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
    <!-- Spring-Test 是 Spring 与 Junit 提供的整合包
    通过这个组件可以在Junit测试时自动对IOC容器进行初始化
    不同版本的 spring-test 组件封装的工具包不一样，请注意不同版本的使用
    否则程序运行会出现意想不到的错误, 本测试项目中 5.1.6 需单独添加 javax.servlet的引用
    -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>5.1.6.RELEASE</version>
    </dependency>
    <!-- 运行Junit测试时加载 Servlet 组件 -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <!--scope 为 test 时表示只有在单元测试时才会加载这个依赖-->
        <scope>test</scope>
    </dependency>
    
## 2、在测试类中指定使用 spring-test 管理
在测试类上方添加注解 @RunWith 绑定 spring-test 测试类
    
    @RunWith(SpringJUnit4ClassRunner.class)
    public class FactoryMapperTest {
        ...
    }
    
在测试类上方添加注解 @ContextConfiguration 指定 spring 配置文件路径
    
    /**
     * SpringJUnit4ClassRunner.class 是 spring-test 提供的整合类
     * Junit 将单元测试管理权交给 Spring，便可以在测试用例启动时
     * 自动加载 applicationContext.xml，并初始化 IOC 容器
     */
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(locations = {"classpath:applicationContext.xml"})
    public class FactoryMapperTest {
        ...
    }
     
## 3、编写 Mapper 测试类 与 Service 测试类
FactoryMapperTest：

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
    
FactoryServiceTest：

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

## 4、测试方法说明
① Junit 的断言方法，此处仅以 assertTrue 示例
    
    Factory factory = factoryService.findById(1);
    // Assert 断言，对程序结果进行判断
    assertTrue("未找到Factory对象", factory != null);

assertTrue方法包含2个参数，第一个是message，第二个是 判断条件。
如果判断条件失败时，会抛出message的错误信息，成功时运行通过。

② Junit 中的 @Transactional 注解

Junit 在单元测试中支持事务提交的方式@Transactional，
在方法执行成功后自动执行回滚操作。在包含 update、insert、delete 操作的测试方法上面可添加
@Transactional 注解进行单元测试防止原始数据不小心被篡改。

    @Test
    @Transactional
    public void initFactory() throws ParseException {
        factoryService.initFactory();
    }

③ Junit 中的 @Rollback 注解
@Rollback 与 @Transactional  配套使用，当 @Rollback 设置为 false 时，
告诉 junit 不要对当前测试执行回滚操作。

    @Test
    @Transactional
    @Rollback(false)
    public void initFactory() throws ParseException {
        factoryService.initFactory();
    }

!注意：当 数据库操作时发生了Mapper类中指定的异常时，仍然会触发事务的回滚操作。

## 5、其他注意的点
① 单元测试中 web资源 的引用问题

在使用Junit进行单元测试时，如果spring找不到WEB-INF中的资源文件，可以手动拷贝
 WEB-INF文件夹 到target->test-classes 目录下。