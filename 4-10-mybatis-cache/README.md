# Mybatis 一级缓存 与 二级缓存

## 1、Mybatis 的缓存机制

默认情况下，Mybatis开启了一级缓存。

在SqlSession的生命周期内，如果执行SQL查询数据库同一对象，
首次查询时会把该对象加载到一级缓存中，之后在该SqlSession的生命周期内再次查询该对象，
则不再向数据库查询，而是直接从一级缓存中进行加载。

此外，只要在SqlSession生命周期中执行了commit指令，
则会清空mapper.xml对应的命名空间下所有的缓存。
默认情况下，insert、update、delete语句都会使缓存清空，
select语句也可以配置强行清空缓存。

## 2、一级缓存与二级缓存的区别

一级缓存的生命周期是在SqlSession中，所以一般仅限当前会话内，存在时间极短。

二级缓存则可以在不同的SqlSession中共享，是全局性的，存在时间较长。

二级缓存的对象在以下情况下会被销毁：
    
    1、SqlSession对该对象的命名空间内的对象执行了commit语句
    2、该对象达到了配置的最长生命周期
    3、二级缓存的对象数量达到了最大配置数量，且该对象满足配置的销毁策略条件
    
对于一些比较稳定的数据可以采用二级缓存进行加载，例如：行政区域、商品种类等。

## 3、检查是否开启了一级缓存的方法

原理：在同一个SqlSession生命周期中执行两次同样的查询语句

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

此时，控制台仅显示了一次SQL查询过程，第二次显示Cache Hit Ratio[SQL_CACHE]:0.5(其中0.5表示缓存命中率，命中率越高，代表优化效果越好)
    
    09:00:32.606 [main] DEBUG factory.findByPageHelper_COUNT - ==>  Preparing: SELECT count(0) FROM factory 
    09:00:32.788 [main] DEBUG factory.findByPageHelper_COUNT - ==> Parameters: 
    09:00:32.916 [main] DEBUG factory.findByPageHelper_COUNT - <==      Total: 1
    09:00:32.917 [main] DEBUG factory.findByPageHelper - ==>  Preparing: select * from factory LIMIT 2 
    09:00:32.918 [main] DEBUG factory.findByPageHelper - ==> Parameters: 
    09:00:32.929 [main] DEBUG factory.findByPageHelper - <==      Total: 2
    com.leon.mybatis.entity.Factory@4f209819
    互联智控
    com.leon.mybatis.entity.Factory@15eb5ee5
    创意工坊：4507
    09:00:32.930 [main] DEBUG SQL_CACHE - Cache Hit Ratio [SQL_CACHE]: 0.5
    com.leon.mybatis.entity.Factory@4f209819
    互联智控
    com.leon.mybatis.entity.Factory@15eb5ee5
    创意工坊：4507
  
## 4、开启二级缓存的方法
4-1. 在mybatis的配置文件中的settings标签配置使用二级缓存

路径：mybatis-config.xml

    <configuration>
        <settings>
            <!-- 开启二级缓存  -->
            <setting name="cacheEnabled" value="true"  />
        </settings>
    </configuration>

4-2. 在mapper文件中定义该命名空间的二级缓存策略

路径：factory_mapper.xml

    <mapper namespace="factory">
         <!--
            cache标签用于配置缓存应用策略
    
                eviction 是缓存清除策略，当缓存数量达到上限后，自动触发对应算法对缓存对象清除
                    1.LRU - 最近最少使用的：移除最长时间不被使用的对象
                    2.FIFO - 先进先出：按对象进入缓存的顺序来移除他们
                    3.SOFT - 软引用：移除基于垃圾回收器状态和软引用规则的对象
                    4.WEAK - 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象
    
                flushInterval 代表间隔多长时间自动清除缓存，单位毫秒，600000毫秒 = 10分钟
                size 缓存存储上线，用于保存对象或集合（1个集合算1个对象）的数量上线
                readOnly 设置为true，代表只读缓存，每次从缓存取出的是对象本身，这种方式执行效率高
                         设置为false，代表每次取出的是缓存对象的副本，每一次取出的对象都是缓存对象的拷贝，这种方式安全性高
          -->

        <cache eviction="LRU" flushInterval="600000" size="512" readOnly="true" />
    </mapper>

## 5、二级缓存测试
路径：src/test/java/com.leon.mybatis/MybatisTestor

    // 二级缓存测试
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
运行该测试方法，得到以下的控制台输出：

    ----------- 一级缓存测试 第一次查询 开始 -----------
    11:10:27.649 [main] DEBUG factory - Cache Hit Ratio [factory]: 0.0
    11:10:27.657 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Opening JDBC Connection
    11:10:28.152 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - Created connection 343856911.
    11:10:28.153 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@147ed70f]
    11:10:28.157 [main] DEBUG factory.findById - ==>  Preparing: select * from factory where id = ? 
    11:10:28.335 [main] DEBUG factory.findById - ==> Parameters: 4(Integer)
    11:10:28.414 [main] DEBUG factory.findById - <==      Total: 1
    com.leon.mybatis.entity.Factory@6fe7aac8
    创意工坊：4507
    ----------- 一级缓存测试 第一次查询 结束 -----------
    ----------- 二级缓存测试 第一次查询 开始 -----------
    11:10:28.415 [main] DEBUG factory - Cache Hit Ratio [factory]: 0.0
    11:10:28.416 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Opening JDBC Connection
    11:10:28.429 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - Created connection 1704629915.
    11:10:28.429 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@659a969b]
    11:10:28.430 [main] DEBUG factory.findById - ==>  Preparing: select * from factory where id = ? 
    11:10:28.430 [main] DEBUG factory.findById - ==> Parameters: 4(Integer)
    11:10:28.435 [main] DEBUG factory.findById - <==      Total: 1
    com.leon.mybatis.entity.Factory@35047d03
    创意工坊：4507
    ----------- 二级缓存测试 第一次查询 结束 -----------
    11:10:28.436 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@659a969b]
    11:10:28.437 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@659a969b]
    11:10:28.437 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - Returned connection 1704629915 to pool.
    ----------- 一级缓存测试 第二次查询 开始 -----------
    11:10:28.437 [main] DEBUG factory - Cache Hit Ratio [factory]: 0.3333333333333333
    com.leon.mybatis.entity.Factory@35047d03
    创意工坊：4507
    ----------- 一级缓存测试 第二次查询 结束 -----------
    ----------- 一级缓存测试 第三次查询 开始 -----------
    11:10:28.437 [main] DEBUG factory - Cache Hit Ratio [factory]: 0.5
    com.leon.mybatis.entity.Factory@35047d03
    创意工坊：4507
    ----------- 一级缓存测试 第三次查询 结束 -----------
    11:10:28.438 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@147ed70f]
    11:10:28.439 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@147ed70f]
    11:10:28.439 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - Returned connection 343856911 to pool.
    ----------- 二级缓存测试 第二次查询 开始 -----------
    11:10:28.440 [main] DEBUG factory - Cache Hit Ratio [factory]: 0.6
    com.leon.mybatis.entity.Factory@6fe7aac8
    创意工坊：4507
    ----------- 二级缓存测试 第二次查询 结束 -----------
    
### 运行测试分析：

此次测试中一共对数据进行了5次查询，查询顺序如下：

第一次：session 执行首次查询，此时向数据库执行了查询请求，得到Factory对象Factory@6fe7aac8, Cache Hit Ratio 为 0；
第二次：session21 执行首次查询，此时向数据库执行了查询请求，得到Factory对象Factory@35047d03, Cache Hit Ratio 为 0；
第三次：session 执行第二次查询，此时并没有向数据库发出查询请求，得到Factory对象Factory@35047d03, Cache Hit Ratio 为 0.3333333333333333；
第四次：session 执行第三次查询，此时并没有向数据库发出查询请求，得到Factory对象Factory@35047d03, Cache Hit Ratio 为 0.5；
第五次：session22 执行首次查询，此时并没有向数据库发出查询请求，得到Factory对象Factory@6fe7aac8, Cache Hit Ratio 为 0.6；

### 运行结果分析：

查询结果预期满足mybatis缓存的查找顺序:二级缓存 -> 一级缓存 -> 数据库，

即当二级缓存(全局缓存)存在时，先从二级缓存中获取数据，然后从一级缓存中获取，一级缓存中没有该对象时，从数据库获取。

开启二级缓存之后，Cache Hit Ratio（缓存命中率）计算的是二级缓存命中率。此时一级缓存也生效，但不纳入Cache Hit Ratio的计算中。
只有当 SqlSession 关闭之后，才往二级缓存中写入数据。

上例中，session21 执行首次查询时，虽然 session 已经执行了查询且得到了 Factory@6fe7aac8 对象，但并没有关闭，
此时二级缓存中并不存在 Factory@6fe7aac8 对象，同时 session21 的一级缓存为空，所以 session21 开始向数据库执行查询请求得到 Factory@35047d03 对象，
然后session21关闭，往二级缓存中写入该查询语句对应的实体 Factory@35047d03。

当 session 执行第二、第三次查询时，此时二级缓存中已经有了sql语句对应的 Factory@35047d03 对象，所以直接从二级缓存中获取。
当 session 第三次查询执行完毕之后，此时session执行close方法，把 session 一级缓存中的对象 Factory@6fe7aac8 写入到二级缓存中，
这时，sql语句对应的实体就更新为 Factory@6fe7aac8，而不再是 Factory@35047d03。

到最后 session22 执行同一条sql语句查询时，此时直接从 二级缓存 获取数据，得到 Factory@6fe7aac8 对象。
    
## 6、禁用二级缓存
只要在查询语句指定 useCache="false" 即可。

    <select id="findAll" resultType="com.leon.mybatis.entity.Factory" useCache="false">
        SELECT * FROM factory
    </select>    

## 7、强制清空缓存

flushCache="true" 在执行完SQL查询后强制清空缓存，并且该SQL结果不存入缓存

    <select id="findAll" resultType="com.leon.mybatis.entity.Factory" useCache="false" flushCache="true">
        SELECT * FROM factory
    </select>
    
默认情况下，insert、update、delete语句都会执行flushCache操作。

