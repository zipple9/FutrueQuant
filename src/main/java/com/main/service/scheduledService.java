package com.main.service;

import com.main.util.DailyDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by wzy on 2019/1/19 12:23
 **/
@Service
public class scheduledService {

    @Autowired
    private DailyDataUtil du;



//    @Scheduled(cron = "0/25 * 9,10,11,13,14,15,21,22,23 ? * MON-FRI")
//    public void getCurrentData(){
//        du.getData();
//    }

//    @Scheduled(cron ="0/5 * * * * *" )
//    public void test(){
//        System.out.println("ttt");
//    }

}

