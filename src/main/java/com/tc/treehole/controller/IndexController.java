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
public class IndexController {
    @GetMapping("/")
    public String index(){

        return "index";
    }
}
