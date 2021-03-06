package com.example.demo.service;

import com.example.demo.pojo.User;

import java.util.List;

public interface IUserService {

    User selectUserById(int userId);

    int insertUser(String userName, String password, Integer userAge);

    int updateUser(String userName, String password,Integer userId);

    int deleteUser(Integer userId);

    void transfor(String userName, Integer userId);

    User selectUserByNameAndPwd(String parseInt, String password);

    User selectUserByName(String principal);

    List<String> selectUserNameByMaxAgeTop(int topN);
}
