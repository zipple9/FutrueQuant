package com.main.service;

import com.main.dao.dailyDataDao;
import com.main.dao.historyDataDao;
import com.main.domain.futureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wzy on 2019/1/19 12:23
 **/
@Service
public class myService {

    @Autowired
    private dailyDataDao dd;
    @Autowired
    private historyDataDao hd;

    public void addRawData(String ctx){
        dd.addRawData(ctx);
    }

    public void addData_M(futureData fd){
        try{
            dd.addData_M(fd);
        }catch(Exception e){}
    }

    public List<futureData> getDailyData(){
        return dd.getData();
    }

    public int addHistroyData(futureData fd){
        return hd.addHistroyData(fd);
    }

    public List<futureData> getHistoryData(){
        return hd.getHistoryData();
    }



}
