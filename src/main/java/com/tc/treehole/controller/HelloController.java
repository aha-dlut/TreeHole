package com.tc.treehole.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
@author TanCheng
@create 2023 -06 -03    
*/
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String Hello(@RequestParam(name = "name") String name, Model model){
        model.addAttribute("name",name);
        return "hello";
    }
}
