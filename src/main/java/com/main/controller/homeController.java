package com.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class homeController {


    @RequestMapping("/getData")
    @ResponseBody
    public void getData(){

    }
}
