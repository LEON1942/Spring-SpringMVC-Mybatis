package com.leon.ssm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.ssm.entity.ParentOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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
