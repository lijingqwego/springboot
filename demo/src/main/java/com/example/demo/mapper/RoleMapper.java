package com.example.demo.mapper;

import com.example.demo.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {

    @Select("SELECT `id` AS roleId,`role_name` AS roleName FROM t_role_info WHERE id = #{roleId};")
    Role selectRoleById(@Param("roleId") int roleId);

}
