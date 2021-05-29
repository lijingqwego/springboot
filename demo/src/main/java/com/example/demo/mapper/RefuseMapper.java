package com.example.demo.mapper;

import com.example.demo.pojo.Refuse;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RefuseMapper {

    @Insert("INSERT INTO t_refuse (`name`, `age`, `sex`, `ageAlis`, `workCity`, `applyCity`, `addressCity`) VALUES (#{name}, #{age}, #{sex}, #{ageAlis}, #{workCity}, #{applyCity}, #{addressCity});")
    int insertRefuse(Refuse refuse);

    @Select("SELECT COUNT(`name`) FROM t_refuse WHERE name = #{name};")
    int selectRefuseByName(String name);
}
