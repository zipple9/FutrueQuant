package com.main.domain.trade;

import com.main.domain.FutureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author wzy
 * @Date 2019/8/4
 */
@Component
public class CurrentStg extends Thread {

    @Autowired
    private Strategy strategy;


    private List<FutureData> fdList;


//    public CurrentStg(List<FutureData> fdList){
//        this.fdList=fdList;
//    }

    public void setFdList(List<FutureData> fdList){
        this.fdList=fdList;
    }

    @Override
    public void run() {
        try {
//            System.out.println(strategy.stg1(fdList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
