package com.main.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.main.domain.FutureData;
import com.main.service.myService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("homeController")
public class homeController {

@Autowired
private myService myService;

    @RequestMapping("/getDailyData")
    @ResponseBody
    public List<FutureData> getDailyData(){
        return myService.getDailyData();
    }

    @RequestMapping("/getHistoryData")
    @ResponseBody
    public List<FutureData> getHistoryData(){
        return myService.getHistoryData();
    }




}
