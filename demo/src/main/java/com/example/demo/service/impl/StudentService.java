package com.example.demo.service.impl;

import com.example.demo.mapper.StudentMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Student;
import com.example.demo.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public List<Student> selectNameList() {
        return studentMapper.selectNameList();
    }
}
