package com.example.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GloableException {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String runtimeException(){
        return "出现异常了";
    }
}
