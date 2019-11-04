# Mybatis-Plus 敏捷开发插件 - 分页插件

官网：[Mybatis-plus](https://mybatis.plus/) 或 [这里](https://mp.baomidou.com/)

## 1、使用 Mybatis-Plus 内置分页分页方法

    QueryWrapper<ParentOrder> qw = new QueryWrapper<>();
    qw.like("receiver_address", "广州");
    IPage page = new Page(1, 10);
    IPage pageResult = pom.selectPage(page, qw);
    
## 2、自定义 SQL分页方法
①、编写mapper中的查询语句，参数用Map承载
    
    <select id="selectSqlPage" parameterType="java.util.Map" resultType="java.util.LinkedHashMap">
        select
        a.order_no, c.order_no, a.receiver, c.stamper_content, a.receiver_address, a.receiver_phone
        from parent_orders a, order_relative_relationship b, orders c
        where a.id = b.parent_order_id and b.child_id = c.id
        and a.receiver_address like CONCAT('%',#{p.receiverAddress},'%')
    </select>

②、在Mapper接口中声明接口方法以及参数类型

    
    // BaseMapper 定义了 CRUD 方法，运行时会自动根据实体的注解生成相应的SQL语句
    public interface ParentOrderMapper extends BaseMapper<ParentOrder> {
        /**
         自定义SQL分页
         @param page，第一个对象固定为分页对象
         @param param，第二个参数 Map 向SQL传递多参数，但要使用@Param()定义前缀
         @return 返回分页查询结果
         */
        public  IPage selectSqlPage(IPage page,@Param("p") Map param);
    }
    
代码执行语句：

    Map map = new HashMap();
    map.put("receiverAddress", "广州");
    IPage page = new Page(1, 10);
    IPage pageResult = pom.selectSqlPage(page, map);
    