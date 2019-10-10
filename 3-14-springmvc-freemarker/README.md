#SpringMVC 整合 freemarker

##1、在pom.xml中添加freemarker的依赖项

    <!-- 整合freemarker -->
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.28</version>
    </dependency>
    <!-- freemarker 整合第三方组件的支持包，用于freemarker的配置和整合工作 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-support</artifactId>
        <version>5.0.8.RELEASE</version>
    </dependency>


##2、在applicationContext.xml中配置处理freemarker的JavaBean

路径：src/main/resources/applicationContext.xml

    
    <!-- FreeMarkerViewResolver是Spring-Context-Support 提供的整合类，
        在IOC容器初始化时通知SpringMVC默认使用freemarker进行数据展现
    -->
    <beans>
        <bean id="ViewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
            <!-- 设置响应输出，解决中文乱码 -->
            <property name="contentType" value="text/html;charset=utf-8"></property>
            <!-- 指定Freemarker模板文件扩展名 -->
            <property name="suffix" value=".ftl"></property>
        </bean>
        
        <bean id="freemarkerConfig" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
            <property name="templateLoaderPath" value="/WEB-INF/ftl"></property>
            <property name="freemarkerSettings">
                <props>
                    <!-- 设置FreeMarker 脚本与数据 渲染时使用的字符集 -->
                    <prop key="defaultEncoding">UTF-8</prop>
                </props>
            </property>
        </bean>
    </beans>




 
 