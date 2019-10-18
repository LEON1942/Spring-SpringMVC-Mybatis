package com.leon.mybatis;

import com.leon.mybatis.entity.Factory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    public void testInsert(){
        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();
            Factory factory = new Factory();

            factory.setName("创意工坊："+(new Random().nextInt(8999) +1000));
            factory.setAdCode("440111");
            factory.setDeleted(false);
            factory.setEnabled(true);
            factory.setLongitude(113.296464f);
            factory.setLatitude(23.348045f);

            int result = session.insert("factory.insert", factory);

            System.out.println(result);
            System.out.println(factory.getId());

            session.commit();
        } catch (Exception e){
            e.printStackTrace();
            if (session != null){
                session.rollback();
            }
        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用欧冠
                session.close();
            }
        }
    }

//    @Test
    public void testUpdate(){
        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();
            Factory factory= session.selectOne("factory.findById", 4);
            System.out.println(factory);
            System.out.println(factory.getName());
            System.out.println(factory.getStatus());

            factory.setStatus("updated status");
            int result = session.update("factory.updateById", factory);
            session.commit();
            System.out.println("----------- update -----------");
            System.out.println(result);
        } catch (Exception e){
            e.printStackTrace();
            if(session != null){
                session.rollback();
            }
        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用欧冠
                session.close();
            }
        }

    }

//    @Test
    public void testDelete(){
        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();
            Factory factory= session.selectOne("factory.findById", 3);
            System.out.println(factory);
            System.out.println(factory.getName());
            System.out.println(factory.getStatus());

            factory.setStatus("updated status");
            int result = session.delete("factory.deleteById", factory.getId());
            session.commit();
            System.out.println("----------- deleted -----------");
            System.out.println(result);
        } catch (Exception e){
            e.printStackTrace();
            if(session != null){
                session.rollback();
            }
        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用欧冠
                session.close();
            }
        }
    }

//    @Test
    public void testSelectAll(){

        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();
            List<Factory> factory_list = session.selectList("factory.findAll");
            for (Factory factory:factory_list) {
                System.out.println(factory);
                System.out.println(factory.getName());
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用
                session.close();
            }
        }
    }


    @Test
    public void testFindByRange(){

        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();
            Map params = new HashMap();
            params.put("min", 0);
            params.put("max", 50);
            params.put("limit", 3);

            List<Factory> factory_list = session.selectList("factory.findByIdRange", params);
            for (Factory factory:factory_list) {
                System.out.println(factory);
                System.out.println(factory.getName());
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用
                session.close();
            }
        }
    }




    @Test
    public void testFindDeviceFactory(){

        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();

            Map map = session.selectOne("factory.findDeviceFactoryMap");
            for (Object key:map.keySet()) {
                System.out.println(key+" "+ map.get(key));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用
                session.close();
            }
        }
    }



}
