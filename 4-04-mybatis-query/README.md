# Mybatis 的Bean 和 Mapper 设置

##1、设置采用驼峰形式转换数据库表名与Bean属性名
路径：resources/mybatis-config.xml
    
    <configuration>
        <settings>
            <!-- 驼峰命名转换 -->
            <setting name="mapUnderscoreToCamelCase" value="true"/>
        </settings>
    </configuration>
    
## 2、创建数据库实体类Orders
路径：com/leon/mybatis/entity/Orders.java
    
    
    public class Orders {
        
        private String id;
        private String orderNo;
        
        ...
        
    }
   
## 3、创建mapper文件
路径：resources/mappers/orders.xml

    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    
    <mapper namespace="orders">
        <select id="selectOrder" resultType="com.leon.mybatis.entity.Orders">
          SELECT * FROM orders WHERE `id` = #{id}
      </select>
    </mapper>
     
## 4、在mybatis配置文件中配置加载mapper文件
路径：resources/mybatis-config.xml

    <configuration>
        <mappers>
            <mapper resource="mappers/orders.xml"/>
        </mappers>
    </configuration>
    
## 5、编写测试类

    package com.leon.mybatis;
    
    import com.leon.mybatis.entity.Orders;
    import org.apache.ibatis.io.Resources;
    import org.apache.ibatis.session.SqlSession;
    import org.apache.ibatis.session.SqlSessionFactoryBuilder;
    import org.apache.ibatis.session.SqlSessionFactory;
    import org.junit.BeforeClass;
    import org.junit.Test;
    
    import java.io.IOException;
    import java.io.Reader;
    import java.sql.Connection;
    
    public class MybatisTester {
    
        static SqlSessionFactory sqlSessionFactory = null;
    
        // BeforeClass 用于在Junit测试用例前执行一次全局化初始工作，这里用来初始化sqlSessionFactory对象
        @BeforeClass
        public static void init() throws IOException {
            //读取配置文件
            Reader reader=Resources.getResourceAsReader("mybatis-config.xml");
            //通过配置文件构建会话工厂类
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }
    
    //    @Test
        public void testSessionFactory() throws IOException {
    
            //建立会话
            SqlSession session = sqlSessionFactory.openSession();
            //打开会话连接
            Connection connection =  session.getConnection();
            //若正常打开会话，则表示配置项无误，若报错则说明配置有误
            System.out.println(connection);
        }
    
        @Test
        public void testGetFirstOrder(){
            String orderId = "001d62ae5ddbf006d4de00c00714bd15";
    
            // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
            SqlSession session = null;
            try{
                session = sqlSessionFactory.openSession();
                Orders order = session.selectOne("orders.selectOrder", orderId);
                System.out.println(order);
                System.out.println(order.getOrderNo());
            } catch (Exception e){
                e.printStackTrace();
            } finally{
                if (session != null){
                    // 将Connection归还连接池供其他session使用欧冠
                    session.close();
                }
            }
        }
    
    
    }
