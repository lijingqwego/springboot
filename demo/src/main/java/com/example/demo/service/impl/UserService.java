package com.example.demo.service.impl;

import com.example.demo.encry.SymmetricEncoder;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserById(int userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public int insertUser(String userName, String password, Integer userAge) {
        return userMapper.insertUser(userName,SymmetricEncoder.AESEncode(password),userAge);
    }

    @Override
    public int updateUser(String userName, String password, Integer userId) {
        return userMapper.updateUser(userName,SymmetricEncoder.AESEncode(password),userId);
    }

    @Override
    public int deleteUser(Integer userId) {
        return userMapper.deleteUser(userId);
    }

    @Override
    @Transactional
    public void transfor(String userName, Integer userId) {
        userMapper.updateUser(userName,"",userId);
        //int a  = 1 / 0;
        userMapper.deleteUser(userId);
    }

    @Override
    public User selectUserByName(String userName, String password) {
        return userMapper.selectUserByName(userName,SymmetricEncoder.AESEncode(password));
    }
}
