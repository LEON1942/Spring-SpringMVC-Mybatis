# Mybatis 的 insert, update, delete 与回滚

##1、insert 语句
路径：resources/mappers/factory_mapper.xml

    <insert id="insert" parameterType="com.leon.mybatis.entity.Factory">
        INSERT INTO `factory`(`name`, `longitude`, `latitude`, `ad_code`, `enabled`, `deleted`)
        VALUES (#{name}, #{longitude}, #{latitude}, #{adCode}, #{enabled}, #{deleted});
        <selectKey resultType="int" keyProperty="id" order="AFTER">
            <!-- 当前连接最后产生的ID号 -->
            select last_insert_id()
        </selectKey>
    </insert>

在执行insert语句时，可通过配置selectKey的方式查找产生的新ID赋值给Bean实例
    
## 2、update 语句
路径：resources/mappers/factory_mapper.xml
 
    <select id="findById" resultType="com.leon.mybatis.entity.Factory">
        <!-- 如果参数是一个基本数据类型或者封装对象，则可以使用#{value}代入即可 -->
        select * from factory where id = #{value}
    </select>

    <update id="updateById" parameterType="com.leon.mybatis.entity.Factory">
        UPDATE `automation_website`.`factory`
        SET `name` = #{name},
        `longitude` = #{longitude},
        `latitude` = #{latitude},
        `ad_code` = #{adCode},
        `enabled` = #{enabled},
        `status` = #{status},
        `deleted` = #{deleted}
        WHERE `id` = #{id};
    </update>
    
## 3、delete 语句
路径：resources/mappers/factory_mapper.xml

    <update id="deleteById" parameterType="int">
        delete from `automation_website`.`factory` WHERE `id` = #{value};
    </update>

## 4、回滚操作
在执行数据库insert、update、delete事件时，需要通过commit方法把改动提交到数据库

如果执行过程中出现错误，需要捕获异常并执行回滚操作

    try{
        session = sqlSessionFactory.openSession();
        Factory factory = new Factory();
        
        ...
        
        session.commit();
    } catch (Exception e){
        e.printStackTrace();
        if (session != null){
            //回滚操作
            session.rollback();
        }
    } finally{
        if (session != null){
            // 将Connection归还连接池供其他session使用欧冠
            session.close();
        }
    }