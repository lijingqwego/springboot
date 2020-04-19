package com.example.demo.service;

import com.example.demo.pojo.Perm;

import java.util.List;

public interface IPermService {

    List<Perm> selectPermListByRoleId(int roleId);
}
