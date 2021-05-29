package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT `user_id` AS userId,`user_name` AS userName,`password`,`user_age` AS userAge FROM t_user_info WHERE user_id = #{userId};")
    User selectUserById(@Param("userId") int userId);

    @Insert("INSERT INTO t_user_info (`user_name`, `password`, `user_age`) VALUES (#{userName}, #{password}, #{userAge});")
    int insertUser(@Param("userName") String userName,  @Param("password")String password, @Param("userAge") Integer userAge);

    @Update("UPDATE t_user_info SET `user_name` = #{userName}, `password` = #{password} WHERE `user_id` = #{userId};")
    int updateUser(@Param("userName") String userName, @Param("password")String password, @Param("userId") Integer userId);

    @Delete("DELETE FROM  t_user_info WHERE `user_id` = #{userId};")
    int deleteUser(@Param("userId") Integer userId);

    @Select("SELECT `user_id` AS userId,`user_name` AS userName,`password`,`user_age` AS userAge FROM t_user_info WHERE user_name = #{userName} AND password = #{password};")
    User selectUserByNameAndPwd(@Param("userName")String userName, @Param("password")String password);

    @Select("SELECT `user_id` AS userId,`user_name` AS userName,`role_id` as roleId FROM t_user_info WHERE user_name = #{userName};")
    User selectUserByName(@Param("userName")String userName);

    @Select("SELECT `user_name` AS userName FROM t_user_info GROUP BY user_name order by user_age desc LIMIT #{topN}")
    List<String> selectUserNameByMaxAgeTop(@Param("topN")int topN);
}
