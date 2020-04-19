package com.example.demo.service.impl;

import com.example.demo.mapper.PermMapper;
import com.example.demo.pojo.Perm;
import com.example.demo.service.IPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermService implements IPermService {

    @Autowired
    private PermMapper permMapper;

    @Override
    public List<Perm> selectPermListByRoleId(int roleId) {
        return permMapper.selectPermListByRoleId(roleId);
    }
}
