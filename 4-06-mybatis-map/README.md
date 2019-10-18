# Mybatis Map的使用

##1、使用Map传入参数
路径：mappers/factory_mapper.xml
    
     <select id="findByIdRange" parameterType="java.util.Map" resultType="com.leon.mybatis.entity.Factory">
        select * from factory where id between #{min} and #{max} order by id desc limit 0, #{limit}
    </select>

设置parameterType为java.util.Map

查询语句：

    Map params = new HashMap();
    params.put("min", 0);
    params.put("max", 50);
    params.put("limit", 3);
    List<Factory> factory_list = session.selectList("factory.findByIdRange", params);

##2、使用Map接收返回值
路径：mappers/factory_mapper.xml

    <select id="findDeviceFactoryMap" resultType="java.util.Map">
        select a.*, b.name as fab_name from device a, factory b where a.fab_id = b.id limit 0, 1
    </select>
  
HashMap 字段排序是按照字段的hash值, 得到的顺序与select字段排序不一致

查询语句：

    Map map = session.selectOne("factory.findDeviceFactoryMap");

##3、使用LinkedHashMap接收返回值
    
    <!-- HashMap 字段排序是按照字段的hash值 -->
    <!--<select id="findDeviceFactoryMap" resultType="java.util.Map">--> 
    <!-- LinkedHashMap保证查询的字段顺序与预期一致 -->
    <select id="findDeviceFactoryMap" resultType="java.util.LinkedHashMap"> 
        select a.*, b.name as fab_name from device a, factory b where a.fab_id = b.id limit 0, 1
    </select>
    
LinkedHashMap 此时得到的返回值字段顺序与select语句字段排列顺序一致


查询语句：

    Map map = session.selectOne("factory.findDeviceFactoryMap");




