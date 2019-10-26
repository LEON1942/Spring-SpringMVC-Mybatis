# Mybatis  1对多配置

## 1、实体类定义
需求描述：Factory 实体类包含多个设备 Device，在查询Factory时把包含的Device列表加载出来。

Device类定义：    
   
    public class Device {
        private Integer id;
        private Integer fabId; // 对应Factory.id
        
        ...
    }

Factory类定义：
    
    public class Factory {
        private Integer id;
        private List<Device> devices;
        
        ...
    }

## 2、device_mapper.xml 文件中配置根据 fabid 查询的方法.
路径：src/main/resources/mappers/device_mapper.xml

    <select id="getDeviceByFabId" resultType="com.leon.mybatis.entity.Device">
        select  * from device where fab_id = #{value}
    </select>
    
## 3、factory_mapper.xml 文件中配置加载 Factory 实例并查询对应 devices 的方法
路径：src/main/resources/mappers/factory_mapper.xml

    <resultMap id="factoryDevicesMap" type="com.leon.mybatis.entity.Factory">
        <id property="id" column="id"></id>
        <collection
                property="devices"
                select="device.getDeviceByFabId"
                column="id">
        </collection>
    </resultMap>
    <select id="findIncludeDevicesById" resultMap="factoryDevicesMap">
        <!-- 如果参数是一个基本数据类型或者封装对象，则可以使用#{value}代入即可 -->
        select * from factory where id = #{value}
    </select>
    
配置说明：

1、根据 id 获取 Factory 实例的 select id 为 findIncludeDevicesById.

2、findIncludeDevicesById 的返回类型需定义为 resultMap，而不是 resultType.

3、resultMap中定义了返回的对象封装格式，须注意以下事项：
    
    一、必须指定返回的数据库实体主键和Java实体类对应的主键属性关系，既：
        <id property="id" column="id"></id>
    二、返回的结果集中配置承载结果集的属性(这里是Factory的devices属性)，
        以及结果集的查询语句(这里是"device.getDeviceByFabId")
        和赋予结果集查询语句的字段名(这里配置的是id字段，对应了getDeviceByFabId中的#{value})
        
## 4、测试语句与查询结果
    
编写测试方法：

    @Test
    public void selectOneToMany(){
        // openSession 创建一个新的SqlSession对象，用来处理增删改查的方法调用
        SqlSession session = null;
        try{
            session = sqlSessionFactory.openSession();
            Factory factory= session.selectOne("factory.findIncludeDevicesById", 1);
            System.out.println(factory);
            System.out.println("factory id:"+factory.getId() + ", factory name:"+ factory.getName());
            for(Device device : factory.getDevices()){
                System.out.println("device: ID-"+device.getId()+", NAME-"+device.getName() +" ,fab_id:"+device.getFabId());
            }
        } catch (Exception e){
            e.printStackTrace();

        } finally{
            if (session != null){
                // 将Connection归还连接池供其他session使用
                session.close();
            }
        }
    }
    
得出的测试结果：

    09:02:35.445 [main] DEBUG factory.findIncludeDevicesById - ==>  Preparing: select * from factory where id = ? 
    09:02:35.635 [main] DEBUG factory.findIncludeDevicesById - ==> Parameters: 1(Integer)
    09:02:35.798 [main] DEBUG device - Cache Hit Ratio [device]: 0.0
    09:02:35.799 [main] DEBUG device.getDeviceByFabId - ====>  Preparing: select * from device where fab_id = ? 
    09:02:35.800 [main] DEBUG device.getDeviceByFabId - ====> Parameters: 1(Integer)
    09:02:35.886 [main] DEBUG device.getDeviceByFabId - <====      Total: 19
    09:02:35.887 [main] DEBUG factory.findIncludeDevicesById - <==      Total: 1
    com.leon.mybatis.entity.Factory@29626d54
    factory id:1, factory name:互联智控
    device: ID-1, NAME-订单调度中心 ,fab_id:1
    device: ID-2, NAME-互联智控1号店 ,fab_id:1
    device: ID-3, NAME-互联智控2号店 ,fab_id:1
    device: ID-4, NAME-广州3代机2005 ,fab_id:1
    device: ID-5, NAME-广州3代机2006 ,fab_id:1
    device: ID-6, NAME-广州3代机2007 ,fab_id:1
    device: ID-7, NAME-广州3代机2008 ,fab_id:1
    device: ID-8, NAME-广州3代机2009 ,fab_id:1
    device: ID-9, NAME-广州3代机2010 ,fab_id:1
    device: ID-10, NAME-广州3代机2011 ,fab_id:1
    device: ID-11, NAME-广州3代机2012 ,fab_id:1
    device: ID-12, NAME-广州3代机2014 ,fab_id:1
    device: ID-13, NAME-广州3代机2013 ,fab_id:1
    device: ID-14, NAME-广州3代机2015 ,fab_id:1
    device: ID-15, NAME-广州3代机2016 ,fab_id:1
    device: ID-16, NAME-广州3代机2017 ,fab_id:1
    device: ID-17, NAME-广州3代机2018 ,fab_id:1
    device: ID-18, NAME-广州3代机2019 ,fab_id:1
    device: ID-19, NAME-广州3代机2020 ,fab_id:1