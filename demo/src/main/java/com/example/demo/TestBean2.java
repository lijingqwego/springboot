package com.example.demo;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("testBean2")
public class TestBean2 {

    public void test2() {
        String rowKey = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        System.out.println("test2:rowKey=>"+rowKey);
    }
}
