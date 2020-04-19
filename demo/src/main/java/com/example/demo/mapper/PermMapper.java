package com.example.demo.mapper;

import com.example.demo.pojo.Perm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermMapper {

    @Select("SELECT `id`,`perm_name` AS permName,`role_id` AS roleId FROM t_perm_info WHERE `role_id` = #{roleId};")
    List<Perm> selectPermListByRoleId(@Param("roleId") int roleId);

}
