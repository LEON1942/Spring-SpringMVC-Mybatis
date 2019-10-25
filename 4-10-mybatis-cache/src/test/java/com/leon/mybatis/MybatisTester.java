package com.leon.mybatis;

import com.github.pagehelper.PageHelper;
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

//    @Test
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


//    @Test
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


//    @Test
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

//    @Test
    public void testDynamicSQL(){
        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();
            Map params = new HashMap();
            params.put("name", "工坊");
            params.put("enabled", true);
            params.put("longitude", 170);

            List<Factory> Factory_list = session.selectList("factory.dynamicSQL", params);

            for (Factory factory : Factory_list) {
                System.out.println(factory.getName()+" "+ factory.getAd_code());
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

    // 一级缓存测试
//    @Test
    public void testPageHelper(){
        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();

            // startPage 会自动对下一次查询进行分页
            PageHelper.startPage(1, 2);

            List<Factory> factory_list = session.selectList("factory.findByPageHelper");

            for (Factory factory:factory_list) {
                System.out.println(factory);
                System.out.println(factory.getName());
            }

            PageHelper.startPage(1, 2);
            List<Factory> factory_list2 = session.selectList("factory.findByPageHelper");

            for (Factory factory:factory_list2) {
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

    // 二级缓存测试
    // 二级缓存隶属于 namespace 级别，相同的 namespace 查询可共享
    @Test
    public void testSecondCache(){

        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{

            System.out.println("----------- 一级缓存测试 第一次查询 开始 -----------");
            session = sqlSessionFactory.openSession();
            Factory factory11= session.selectOne("factory.findById", 4);
            System.out.println(factory11);
            System.out.println(factory11.getName());
            System.out.println("----------- 一级缓存测试 第一次查询 结束 -----------");


            SqlSession session21 = null;
            try{
                session21 = sqlSessionFactory.openSession();

                System.out.println("----------- 二级缓存测试 第一次查询 开始 -----------");
                Factory factory= session21.selectOne("factory.findById", 4);
                System.out.println(factory);
                System.out.println(factory.getName());
                System.out.println("----------- 二级缓存测试 第一次查询 结束 -----------");
            } catch (Exception e){
                e.printStackTrace();
            } finally{
                if (session21 != null){
                    // 将Connection归还连接池供其他session使用欧冠
                    session21.close();
                }
            }

            System.out.println("----------- 一级缓存测试 第二次查询 开始 -----------");
            Factory factory12= session.selectOne("factory.findById", 4);
            System.out.println(factory12);
            System.out.println(factory12.getName());
            System.out.println("----------- 一级缓存测试 第二次查询 结束 -----------");

            System.out.println("----------- 一级缓存测试 第三次查询 开始 -----------");
            Factory factory13= session.selectOne("factory.findById", 4);
            System.out.println(factory13);
            System.out.println(factory13.getName());
            System.out.println("----------- 一级缓存测试 第三次查询 结束 -----------");

        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用欧冠
                session.close();
            }
        }

        SqlSession session22 = null;
        try{
            session22 = sqlSessionFactory.openSession();

            System.out.println("----------- 二级缓存测试 第二次查询 开始 -----------");
            Factory factory= session22.selectOne("factory.findById", 4);
            System.out.println(factory);
            System.out.println(factory.getName());
            System.out.println("----------- 二级缓存测试 第二次查询 结束 -----------");
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (session22 != null){
                // 将Connection归还连接池供其他session使用欧冠
                session22.close();
            }
        }
    }


}
