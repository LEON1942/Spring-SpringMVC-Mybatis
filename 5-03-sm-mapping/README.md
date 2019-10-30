# Spring 与 Mybatis 基本整合案例 - Mapper 接口开发

## 1、Mapper's XML namespace 指向接口，不能随意书写
路径：src/main/resources/mappers/factory_mapper.xml
    
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    
    <mapper namespace="com.leon.ssm.mapper.FactoryMapper">
        <select id="findById" parameterType="Integer" resultType="com.leon.ssm.entity.Factory">
            select * from factory where id = #{value}
        </select>
        <select id="findByCondition" parameterType="java.util.Map" resultType="com.leon.ssm.entity.Factory">
            select * from factory
            where
              id >= #{minId}
              and enabled = #{enabled}
        </select>

mapper 的 namespace 需指向对应的接口类。

## 2、Mapper SQL ID 必须与接口方法名重合
mapper中的方法id需与接口类中的方法名保持一致，例：FactoryMapper
    
    public interface FactoryMapper {
    
        public Factory findById(Integer factoryId);
    
        // public List<Factory> findByCondition(Map map);
    
        // 用@Param的方式定义参数，mybatis 会自动将参数封装为Map对象，@Param()里边是Map的key， value是传入的参数值
        public List<Factory> findByCondition(@Param("minId") Integer minId, @Param("enabled") Boolean enabled);
    }
    
    
    
## 3、接口方法参数会直接传入到SQL中，多参数用@Param进行说明
用@Param的方式定义参数，mybatis 会自动将参数封装为Map对象，@Param()里边是Map的key， value是传入的参数值
    
    public List<Factory> findByCondition(@Param("minId") Integer minId, @Param("enabled") Boolean enabled);

## 4、方法返回值可以是List，也可以是单个对象，Mybatis会自行判断

## 5、添加logback依赖logback-classic在控制台显示sql语句

    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
    </dependency>




