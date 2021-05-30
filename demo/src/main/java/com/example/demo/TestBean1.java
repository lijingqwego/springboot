package com.example.demo;

import org.springframework.stereotype.Component;

@Component("testBean1")
public class TestBean1 {

    public void test1(String test) {
        System.out.println(test);
    }
}
