# Spring 与 SpringMVC 基本整合案例

## 1、配置pom.xml
配置内容：内嵌式Jetty容器、SpringMVC的web插件、SpringMVC的JSON序列化插件Jackson、Freemarker插件

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.leon</groupId>
        <artifactId>ssm</artifactId>
        <version>1.0-SNAPSHOT</version>
    
        <!-- 项目打包为Jetty支持的war包格式 -->
        <packaging>war</packaging>
    
        <!-- aliyun Maven仓库 -->
        <repositories>
            <repository>
                <id>aliyun</id>
                <name>aliyun</name>
                <url>https://maven.aliyun.com/repository/public</url>
            </repository>
        </repositories>
    
        <dependencies>
    
            <!-- SpringMVC 核心web组件 -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-webmvc</artifactId>
                <version>5.1.6.RELEASE</version>
            </dependency>
    
            <!-- SpringMVC 支持的JSON序列化工具包Jackson -->
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-core</artifactId>
                <version>2.9.8</version>
            </dependency>
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-annotations</artifactId>
                <version>2.9.8</version>
            </dependency>
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-databind</artifactId>
                <version>2.9.8</version>
            </dependency>
    
            <!-- freemarker 核心 jar -->
            <dependency>
                <groupId>org.freemarker</groupId>
                <artifactId>freemarker</artifactId>
                <version>2.3.28</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context-support</artifactId>
                <version>5.1.6.RELEASE</version>
            </dependency>
        </dependencies>
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.eclipse.jetty</groupId>
                    <!-- 嵌入式的jetty容器 -->
                    <artifactId>jetty-maven-plugin</artifactId>
                    <version>9.4.20.v20190813</version>
                    <configuration>
                        <webApp>
                            <!-- 解决网页热部署的问题 -->
                            <defaultsDescriptor>src/main/resources/webdefault.xml</defaultsDescriptor>
                        </webApp>
                        <!-- 应用启动后加载webapp网页与资源 -->
                        <webAppSourceDirectory>src/main/webapp</webAppSourceDirectory>
                        <httpConnector>
                            <port>8086</port>
                        </httpConnector>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    
    </project>

## 2、在web.xml中配置Servlet与Filter
路径：src/main/webapp/WEB-INF/web.xml
    
    <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns="http://xmlns.jcp.org/xml/ns/javaee"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
             version="3.1">
    
        <servlet>
            <servlet-name>springmvc</servlet-name>
            <!-- 用于拦截请求，创建对应的Controller进行处理 -->
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
                <!-- 指定在DispatcherServlet初始化时加载的配置文件 -->
                <param-name>contextConfigLocation</param-name>
                <param-value>classpath:applicationContext.xml</param-value>
            </init-param>
            <!-- 在启动时对servlet进行加载，0代表被优先加载 -->
            <load-on-startup>0</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>springmvc</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
        
        <filter>
            <filter-name>characterFilter</filter-name>
            <!-- CharacterEncodingFilter 将Post请求中的参数字符集设置为 UTF-8 -->
            <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
            <init-param>
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>characterFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    
    </web-app>
    
## 3、在applicationContext.xml中进行相应的SpringMVC详细配置。
路径：src/main/resources/applicationContext.xml 
    
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:mvc="http://www.springframework.org/schema/mvc"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
                    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
                    http://www.springframework.org/schema/context
                    https://www.springframework.org/schema/context/spring-context.xsd
                    http://www.springframework.org/schema/mvc
                    http://www.springframework.org/schema/mvc/spring-mvc.xsd
                    ">
    
        <!-- 启用Spring注解形式扫描对象 -->
        <context:component-scan base-package="com.leon" />
    
        <!-- 启用Spring MVC的注解形式 -->
        <mvc:annotation-driven>
            <mvc:message-converters>
                <!-- StringHttpMessageConverter 用于设置文本类型的http相应设置 -->
                <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                    <property name="supportedMediaTypes">
                        <list>
                            <!-- 相应输出的文本浏览器作为html进行解析，使用字符集为UTF-8 -->
                            <value>text/html;charset=utf-8</value>
                        </list>
                    </property>
                </bean>
            </mvc:message-converters>
        </mvc:annotation-driven>
    
        <!-- 将静态资源排除在外，用于提高执行效率 -->
        <mvc:default-servlet-handler/>
    
        <!-- FreeMarkerViewResolver 是 spring-context-support 提供的整合类
             在IOC容器初始化时通知 SpringMVC 默认使用 freemarker 进行数据展现
         -->
        <bean id="ViewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
            <!-- 设置相应输出，并解决中文乱码 -->
            <property name="contentType" value="text/html;charset=utf-8"/>
            <!-- 指定freemarker模板文件扩展名 -->
            <property name="suffix" value=".ftl" />
        </bean>
        <!-- Freemarker 设置类 -->
        <bean id="freemarkerConfig" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
            <!-- 配置Freemarker页面模板资源目录 -->
            <property name="templateLoaderPath" value="/WEB-INF/ftl" />
            <!-- 其他模板引擎设置 -->
            <property name="freemarkerSettings">
                <props>
                    <!-- 设置Freemarker脚本与数据渲染时使用的字符集 -->
                    <prop key="defaultEncoding">UTF-8</prop>
                </props>
            </property>
        </bean>
    
    </beans>
    
## 4、编写freemarker的页面模板与测试路由



