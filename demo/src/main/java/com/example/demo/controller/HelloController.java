package com.example.demo.controller;

import com.example.demo.pojo.Refuse;
import com.example.demo.pojo.User;
import com.example.demo.service.IRefuseService;
import com.example.demo.service.IStudentService;
import com.example.demo.service.IUserService;
import com.example.demo.service.impl.DroolsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class HelloController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IRefuseService iRefuseService;

    @Autowired
    private DroolsService droolsService;

    @PostMapping("/hello")
    public String hello(Refuse refuse) {
//        int topN = 4;
//        List<String> list = iUserService.selectUserNameByMaxAgeTop(topN);
//        List<Student> userList = iStudentService.selectNameList();
//        userList.sort(Comparator.comparingInt(o -> list.indexOf(o.getName())));
//        Map<String, List<Student>> groupBySex = userList.stream().collect(Collectors.groupingBy(Student::getName, LinkedHashMap::new,Collectors.toList()));
//        for (Map.Entry<String, List<Student>> entryUser : groupBySex.entrySet()) {
//            List<Student> entryUserList = entryUser.getValue();
//            for (Student user : entryUserList) {
//                System.out.println(user.getName()+"=>"+user.getId());
//            }
//        }
        droolsService.check("ageAlis", new ArrayList<>(), new Object[]{refuse}, "baseMessage");
        Object ageAlis = droolsService.getGlobal("ageAlis");
        if (ageAlis != null) {
            refuse.setAgeAlis(ageAlis.toString());
        }
        boolean flag = iRefuseService.addRefuse(refuse);
        return flag ? "success" : "failed";
    }

    @RequiresRoles(value = {"ordinary"})
    @GetMapping("/getUser")
    public User getUser(Integer userId) {
        return iUserService.selectUserById(userId);
    }

    @RequiresRoles(value = {"admin"})
    @GetMapping("/deleteUser")
    public int deleteUser(Integer userId) {
        return iUserService.deleteUser(userId);
    }

    @RequiresRoles(value = {"system"})
    @PostMapping("/updateUser")
    public int updateUser(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("userId") Integer userId) {
        return iUserService.updateUser(userName, password, userId);
    }

    @PostMapping("/saveUser")
    public int saveUser(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("userAge") Integer userAge) {
        return iUserService.insertUser(userName, password, userAge);
    }

    @PostMapping("/transfor")
    public String transfor(String userName, Integer userId) {
        iUserService.transfor(userName, userId);
        return "success";
    }

    @RequestMapping("/login")
    public String login(String userName, String password) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (Exception e) {
            return "login fail!";
        }
        return "login success!";
    }
}

