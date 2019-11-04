# Mybatis-Plus 敏捷开发插件 - QueryWrapper

官网：[Mybatis-plus](https://mybatis.plus/) 或 [这里](https://mp.baomidou.com/)

## 1、使用 QueryWrapper 对象传递查询条件 "等于"

    QueryWrapper<ParentOrder> qw = new QueryWrapper<ParentOrder>();
    qw.eq("order_no", "PR0SKQQTM020190511103408CZK7WE9C");
    qw.last("limit 1");
    ParentOrder poder = pom.selectOne(qw);

若selectOne()查询结果不是唯一的话，需要在qw对象手动添加qw.last("limit 1")
    
## 2、使用 QueryWrapper 的 between 设置查询条件范围

    QueryWrapper<ParentOrder> qw2 = new QueryWrapper<>();
    qw2.between("total_price", 100, 2000);

    List<ParentOrder> orders = pom.selectList(qw2);
    
## 3、使用 QueryWrapper 的 like 进行模糊查询

    QueryWrapper<ParentOrder> qw3 = new QueryWrapper<>();
    qw3.like("receiver_address", "广州");
    // qw3.likeLeft("receiver_address", "广州");
    // qw3.likeRight("receiver_address", "广州");

    List<ParentOrder> orders2 = pom.selectList(qw3);
    
## 4、更多使用方法请查看[官方文档](https://mybatis.plus/guide/wrapper.html#alleq)

