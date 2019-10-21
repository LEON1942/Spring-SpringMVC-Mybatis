# Mybatis 动态SQL

##1、Mybatis 中文文档

https://mybatis.org/mybatis-3/zh/getting-started.html
 
##2、mybatis 动态语句

https://mybatis.org/mybatis-3/zh/dynamic-sql.html
    
    <select id="dynamicSQL" resultType="com.leon.mybatis.entity.Factory" parameterType="java.util.Map">
        <bind name="name" value="'%' + name + '%'" />
        select * from factory
        <where>
          <if test="name != null">
            and name like #{name}
          </if>
          <if test="enabled != null">
            and enabled = #{enabled}
          </if>
          <if test="longitude != null and longitude &lt; 180 ">
             and longitude &lt; #{longitude}
          </if>
        </where>
    </select>

以上写法等价于：
    
    <select id="dynamicSQL" resultType="com.leon.mybatis.entity.Factory" parameterType="java.util.Map">
        <bind name="name" value="'%' + name + '%'" />
        select * from factory
        where 1=1
          <if test="name != null">
            and name like #{name}
          </if>
          <if test="enabled != null">
            and enabled = #{enabled}
          </if>
          <if test="longitude != null and longitude &lt; 180 ">
             and longitude &lt; #{longitude}
          </if>
    </select>

更多Mybatis的动态语法结构请参考链接中的官方文档。s

