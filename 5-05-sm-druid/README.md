# Spring 与 Mybatis 基本整合案例 - Druid 连接池配置

## 1、在 applicationContext.xml 中配置 DruidDataSource 相关bean

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"  init-method="init" destroy-method="close">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
        <property name="url" value="jdbc:mysql://localhost:3306/automation_website?useUnicode=true&amp;characterEncoding=UTF-8&amp;useSSL=false&amp;serverTimezone=GMT%2B8" />
        <property name="username" value="root" />
        <property name="password" value="root" />
        <!--在初始化连接池的时候默认创建5个链接-->
        <property name="initialSize" value="5" />
        <!--最大连接数量-->
        <property name="maxActive" value="20"/>
        <!--设置空闲连接数最少为5个-->
        <property name="minIdle" value="5"/>
        <!--数据库连接创建最大等待时间60秒-->
        <property name="maxWait" value="60000" />
        <!--是否在分配连接时检测连接有效性-->
        <property name="testOnBorrow" value="false" />
        <!--是否在连接空闲状态时检测连接有效性-->
        <property name="testWhileIdle" value="true" />
        <!--是否在归还时检测连接有效性-->
        <property name="testOnReturn" value="false"/>
        <!--测试有效性的SQL语句-->
        <property name="validationQuery" value="SELECT 1" />
        <!--filter 用于开启监控过滤器
            stat 是SQL及Web监控
            wall 是预防SQL注入攻击
            slf4j 是日志输出-->
        <property name="filters" value="stat,wall,slf4j"/>
        <!--mergeSql 对相同SQL进行合并
            slowSqlMillis定义超过500毫秒执行时间的SQL为慢查询-->
        <property name="connectProperties" value="druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500"/>
        <!-- asyncInit是1.1.4中新增加的配置，如果有initialSize数量较多时，打开会加快应用启动时间 -->
        <property name="asyncInit" value="true" />
    </bean>

## 2、在applicationContext.xml中配置 servlet 


