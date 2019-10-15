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
