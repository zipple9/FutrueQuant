package com.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by wzy on 2019/1/17 16:44
 **/



@SpringBootApplication
@EnableScheduling
@MapperScan("com.main.dao")
public class app {
    public static void main (String args[]){
        SpringApplication.run(app.class,args);
    }
}
