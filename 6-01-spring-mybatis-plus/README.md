# Mybatis-Plus 敏捷开发插件

## 1、Mybatis-Plus 简介
* Mybatis-Plus（简称MP）是一个Mybatis的增强工具
* MP 在 mybatis 的基础上只做增强不做更改
* 为简化开发、提高效率而生

## 2、Mybatis-Plus 特性
① 无侵入；② 损耗小；③ 强大的CRUD；④ 多种数据库支持；⑤ 内置分页；⑥ 内置主键生成策略；

## 3、Mybatis-Plus 开发方式
* 1、pom.xml 添加 mybatis-plus 依赖项
   
       
    <!--Mybatis-plus 核心组件 -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus</artifactId>
        <version>3.2.0</version>
    </dependency>

注：MP的 v3.1.0之后的版本中添加了对 mybatis 与 mybatis-spring 的引用，不需要再 pom.xml 中另外添加依赖项，否则可能导致版本冲突

* 2、SessionFactory 更改为 MybatisSqlSessionFactoryBean
    
    
    <!--mybatisSqlSessionFactoryBean对mybatis-spring整合插件进行了重写与扩展，提供自动生成SQL功能-->
    <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">

* 3、实体类增加注解描述映射关系
    
    
    //TableName设置实体与表的映射关系
    @TableName("parent_orders")
    public class ParentOrder {
    
        // TableId 用于设置主键属性
        // IdType.AUTO 是指利用MySQL底层主键生成策略自动生成主键
        // 主键生成后会自动回填到id属性
        @TableId(type = IdType.AUTO)
        private String id;
        private String orderNo;
        private Date builtTime;
        //如果属性与字段名对应不上，则需要使用 @TableField 进行说明
        @TableField("built_user_id")
        private Integer ownerId;
        
        ...
    }
    
* 4、Mapper 接口继承 BaseMapper

    
    // BaseMapper 定义了 CRUD 方法，运行时会自动根据实体的注解生成相应的SQL语句
    public interface ParentOrderMapper extends BaseMapper<ParentOrder> {
        ...    
    }
    
    
    
    