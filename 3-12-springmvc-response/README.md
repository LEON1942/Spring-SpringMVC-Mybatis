#响应处理的两种方式

##1、@ResponseBody 产生响应文本


##2、ModelAndView - 利用模板引擎渲染输出


利用MVC框架直接对响应JavaBean时，需要进行序列化输出，

此时需要在pom.xml中添加以下依赖：

     <!-- springMVC 支持的 JSON 序列化工具包 JACKSON -->
     <dependency>
         <groupId>com.fasterxml.jackson.core</groupId>
         <artifactId>jackson-core</artifactId>
         <version>2.9.6</version>
     </dependency>
     <dependency>
         <groupId>com.fasterxml.jackson.core</groupId>
         <artifactId>jackson-annotations</artifactId>
         <version>2.9.6</version>
     </dependency>
     <dependency>
         <groupId>com.fasterxml.jackson.core</groupId>
         <artifactId>jackson-databind</artifactId>
         <version>2.9.6</version>
     </dependency>
     
对于JavaBean对象的Date类型属性，JSON默认输出1970年到现今的时间戳，时区为格林尼治GMT

所以我们需要针对Date属性进行格式化输出，并设置相应时区，配置如下：

    
    public class User {
    
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date birthday;
    }
    




 
 