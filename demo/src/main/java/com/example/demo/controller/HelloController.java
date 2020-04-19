package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class HelloController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/hello")
    public String hello(){
        int a = 1/0;
        return "hello"+a;
    }

    @GetMapping("/getUser")
    public User getUser(Integer userId){
        return iUserService.selectUserById(userId);
    }

    @GetMapping("/deleteUser")
    public int deleteUser(Integer userId){
        return iUserService.deleteUser(userId);
    }

    @PostMapping("/updateUser")
    public int updateUser(@RequestParam("userName") String userName, @RequestParam("password")String password, @RequestParam("userId")Integer userId){
        return iUserService.updateUser(userName,password,userId);
    }

    @PostMapping("/saveUser")
    public int saveUser(@RequestParam("userName") String userName, @RequestParam("password")String password, @RequestParam("userAge")Integer userAge){
        return iUserService.insertUser(userName,password,userAge);
    }

    @PostMapping("/transfor")
    public String transfor(String userName,Integer userId){
        iUserService.transfor(userName,userId);
        return "success";
    }

    @RequestMapping("/login")
    public String login(String userName,String password){
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        }catch (Exception e){
            return "login fail!";
        }
        return "login success!";
    }
}

