# 配置 Mybatis 插件

##1、引入Mybatis的2个依赖项
路径：pom.xml

    <dependencies>
        <!-- Mysql JDBC驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.12</version>
        </dependency>
        <!-- Mybatis 核心依赖 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
     
注：若国内网络无法直接下载mybatis的依赖项，可以配置仓库为阿里云的公开仓库
路径：pom.xml
    
    <repositories>
        <repository>
            <id>aliyun</id>
            <name>aliyun</name>
            <!-- maven.aliyun.com -->
            <url>https://maven.aliyun.com/repository/public</url>
        </repository>
    </repositories>
 
## 2、配置mysql的环境信息，包括驱动、数据库、用户名、密码等
路径：resources/mybatis-config.xml
    
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <!-- https://mybatis.org/mybatis-3/zh/getting-started.html  Mybatis 中文文档 -->
    
    <configuration>
        <!-- 设置默认指向的数据库 -->
        <environments default="dev">
            <!-- 配置环境，不同的环境使用不同的id名称 -->
            <environment id="dev">
                <!-- 事务处理方式 -->
                <transactionManager type="JDBC"/>
                <!-- 采用连接池对数据库连接进行管理 -->
                <dataSource type="POOLED">
                    <property name="driver" value="com.mysql.jdbc.Driver"/>
                    <property name="url" value="jdbc:mysql://localhost:3306/automation_website?useUnicode=true&amp;characterEncoding=UTF-8"/>
                    <property name="username" value="root"/>
                    <property name="password" value="root"/>
                </dataSource>
            </environment>
            <environment id="prd">
                <!-- 事务处理方式 -->
                <transactionManager type="JDBC"/>
                <!-- 采用连接池对数据库连接进行管理 -->
                <dataSource type="POOLED">
                    <property name="driver" value="com.mysql.jdbc.Driver"/>
                    <property name="url" value="jdbc:mysql://8.8.8.8:3238/automation_website?useUnicode=true&amp;characterEncoding=UTF-8"/>
                    <property name="username" value="root"/>
                    <property name="password" value="root"/>
                </dataSource>
            </environment>
        </environments>
    </configuration>
    
## 3、创建 Mybatis 连接测试类
路径：test/java/com.leon.mybatis/MybatisTester

    package com.leon.mybatis;
    
    import org.apache.ibatis.io.Resources;
    import org.apache.ibatis.session.SqlSession;
    import org.apache.ibatis.session.SqlSessionFactoryBuilder;
    import org.apache.ibatis.session.SqlSessionFactory;
    import org.junit.Test;
    
    import java.io.IOException;
    import java.io.Reader;
    import java.sql.Connection;
    
    public class MybatisTester {
    
        @Test
        public void testSessionFactory() throws IOException {
            //读取配置文件
            Reader reader=Resources.getResourceAsReader("mybatis-config.xml");
            //通过配置文件构建会话工厂类
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            //建立会话
            SqlSession session = sessionFactory.openSession();
            //打开会话连接
            Connection connection =  session.getConnection();
            //若正常打开会话，则表示配置项无误，若报错则说明配置有误
            System.out.println(connection);
        }
    }

右键运行该文件即可.
