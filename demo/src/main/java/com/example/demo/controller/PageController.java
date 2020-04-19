package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @RequestMapping("/index")
    public String index(Model model){
        List<String> list = new ArrayList<String>();
         list.add("AAAAA");
         list.add("BBBBB");
         list.add("CCCCC");
         list.add("DDDDD");
         list.add("EEEEE");
         list.add("FFFFF");
        model.addAttribute("list",list);
        model.addAttribute("mobile","13077916560");
        model.addAttribute("name","lijing");
        return "index";
    }

}
