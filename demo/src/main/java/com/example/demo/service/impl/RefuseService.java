package com.example.demo.service.impl;

import com.example.demo.mapper.RefuseMapper;
import com.example.demo.pojo.Refuse;
import com.example.demo.service.IRefuseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefuseService implements IRefuseService {

    @Autowired
    private RefuseMapper refuseMapper;

    @Override
    public boolean addRefuse(Refuse refuse) {
        int count = refuseMapper.selectRefuseByName(refuse.getName());
        return count <= 0 && refuseMapper.insertRefuse(refuse) > 0;
    }
}
