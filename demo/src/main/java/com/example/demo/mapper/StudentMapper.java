package com.example.demo.mapper;

import com.example.demo.pojo.Student;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Vector;

@Mapper
public interface StudentMapper {

    @Select("SELECT id,name FROM t_student")
    List<Student> selectNameList();

    @Select({"<script>",
            "SELECT * FROM t_student ",
            "<where>",
            "<if test='name!=null'>",
            "name LIKE CONCAT('%', #{name}, '%')",
            "</if>",
            "</where>",
            "</script>"})
    Vector<Student> getStudentList(@Param("name") String name);

    @Select("SELECT * FROM t_student")
    Vector<Student> getAllStudentList();

    @Select({"<script>",
            "SELECT * FROM t_student",
            "<where>",
            "<if test=\"name!=null or name!=''\">",
            "name LIKE '%'||#{name}||'%'",
            "</if>",
            "</where>",
            "</script>"})
    Vector<Student> getStudentListByName(@Param("name") String name);

    @Select("SELECT * FROM t_student where no=#{no,jdbcType=VARCHAR}")
    Student getStudentInfo(@Param("no") String no);

    @Insert("insert into t_student (no,name,gender,age,place,dept)" +
            "values(#{student.no},#{student.name},#{student.gender},#{student.age},#{student.place},#{student.dept})")
    void addStudent(@Param("student") Student student);

    @Insert({
            "<script>",
            "insert into t_student(no,name,gender,age,place,dept) values",
            "<foreach collection='students' item='item' index='index' separator=','>",
            "(#{item.no},#{item.name},#{item.gender},#{item.age},#{item.place},#{item.dept})",
            "</foreach>",
            "</script>"
    })
    void addStudentList(@Param(value = "students") Vector<Student> students);

    @Delete("delete from t_student where no=#{no} or name=#{name}")
    void deleteStudent(@Param("no") String no, @Param("name") String name);

    @Update({"<script>",
            "update t_student ",
            "<set>",
            "<if test='student.name != null'>",
            "name=#{student.name},",
            "</if>",
            "<if test='student.gender != null'>",
            "gender=#{student.gender},",
            "</if>",
            "<if test='student.age != null'>",
            "age=#{student.age},",
            "</if>",
            "<if test='student.place != null'>",
            "place=#{student.place},",
            "</if>",
            "<if test='student.dept != null'>",
            "dept=#{student.dept}",
            "</if>",
            "</set>",
            "where no=#{student.no}",
            "</script>"})
    void updateStudent(@Param(value = "student") Student student);

    @Delete({"<script>",
            "delete from t_student where no in ",
            "<foreach collection='noList' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>",})
    void batchDeleteStudent(@Param(value = "noList") List<String> noList);
}
