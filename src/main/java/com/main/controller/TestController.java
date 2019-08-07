package com.main.controller;

import com.main.domain.trade.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/08/07
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    Strategy strategy;


    @RequestMapping("/test")
    public String test(){
        log.info("/test");
        strategy.runStrategyCct();
        return "test";
    }
}
