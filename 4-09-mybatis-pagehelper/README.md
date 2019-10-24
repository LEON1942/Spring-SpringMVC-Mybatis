# Mybatis PageHelper的使用

## 1、Maven中引入 PageHelper 与 jsqlparser 

PageHelper 的官方地址及文档：

    https://pagehelper.github.io/

路径：pom.xml

    <!-- pageHelper 核心依赖 -->
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper</artifactId>
        <version>5.0.4</version>
    </dependency>

    <!-- jsqlparser SQL文本解析器，官方推荐0.9.5，不随意变动 -->
    <dependency>
        <groupId>com.github.jsqlparser</groupId>
        <artifactId>jsqlparser</artifactId>
        <version>0.9.5</version>
    </dependency>

注意：pageHelper 的版本号 必须 与 jsqlparser 的版本号相对应, 如果版本号不对应，则运行时可能会报以下错误：

    java.lang.NoSuchMethodError: net.sf.jsqlparser.statement.select.PlainSelect.getGroupBy()
    ...
 
官方推荐的jsqlparser版本号为 0.9.5

## 2、mybatis-config.xml 增加Plugin配置

路径：src/main/resources/mybatis-config.xml

    <configuration>
        <plugins>
            <plugin interceptor="com.github.pagehelper.PageInterceptor">
                <property name="helperDialect" value="mysql" />
                <!-- 分页合理化 -->
                <property name="reasonable" value="true"/>
            </plugin>
        </plugins>
    </configuration>
    
在configuration的配置项中，应该遵循dtd文件的配置项定义顺序从上往下配置：

    properties?, settings?, typeAliases?, typeHandlers?,
    objectFactory?,objectWrapperFactory?, plugins?,
    environments?, databaseIdProvider?, mappers?

## 3、代码使用PageHelper.startPage()自动分页

在mybatis执行查询语句前添加一句：
    
    PageHelper.startPage(pageNum, pageSize);

则 PageHelper 会先查询满足sql语句的数据库项目总数，然后自动为sql语句添加 limit 语句分页

原业务逻辑不需要进行额外的变更。