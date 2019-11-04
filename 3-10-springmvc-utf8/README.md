# 中文乱码的配置

## 1、Response响应乱码-Spring配置StringHttpMessageConverter

位置：项目目录/src/main/resources/applicationContext.xml

    <!-- 启用MVC的注解模式 -->
        <mvc:annotation-driven>
            <mvc:message-converters>
                <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                    <!-- 配置支持的媒体类型(MIME) -->
                    <property name="supportedMediaTypes">
                        <list>
                            <value>text/html;charset=utf-8</value>
                        </list>
                    </property>
                </bean>
            </mvc:message-converters>
        </mvc:annotation-driven>

## 2、Get请求乱码-server.xml增加URIEncoding属性(Jetty无需设置)

位置：tomcat目录/conf/server.xml


    <Server>
        <Connector URIEncoding="UTF-8">
    
        </Connector>
    </Server>

## 3、Post请求乱码-web.xml配置CharacterEncodingFilter(Jetty无需配置)

位置：项目目录/src/main/webapp/WEB-INF/web.xml
    
    <web-app>
        <filter>
            <filter-name>charaterfilter</filter-name>
            <!-- CharacterEncodingFilter 将Post请求中的参数字符集设置为UTF-8-->
            <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
            <init-param>
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>charaterfilter</filter-name>
            <url-pattern>/</url-pattern>
        </filter-mapping>
    </web-app>
