package com.example.demo;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("testBean1")
public class TestBean1 {

    public void test1(String test) {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        System.out.println("test1:rowKey=>"+date);
    }
}
