package com.example.demo.service.impl;

import com.example.demo.mapper.RoleMapper;
import com.example.demo.pojo.Role;
import com.example.demo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role selectRoleById(int roleId) {
        return roleMapper.selectRoleById(roleId);
    }
}
