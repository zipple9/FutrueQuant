package com.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author wzy
 * @Date 2019/9/3
 */
@Configuration
public class MyConfig {



    @Bean
    public ExecutorService getThreadPool(){
        return Executors.newFixedThreadPool(3);
    }
}
