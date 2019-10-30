package com.leon.ssm.mapper;

import com.leon.ssm.entity.Factory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FactoryMapper {

    public Factory findById(Integer factoryId);

//    public List<Factory> findByCondition(Map map);

    // 用@Param的方式定义参数，mybatis 会自动将参数封装为Map对象，@Param()里边是Map的key， value是传入的参数值
    public List<Factory> findByCondition(@Param("minId") Integer minId, @Param("enabled") Boolean enabled);
}
