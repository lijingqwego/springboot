package com.example.demo.mapper;

import com.example.demo.pojo.Student;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT id,name FROM t_student")
    List<Student> selectNameList();
}
