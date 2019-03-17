package com.main.service;

import com.main.dao.dailyDataDao;
import com.main.domain.futureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wzy on 2019/1/19 12:23
 **/
@Service
public class myService {

    @Autowired
    private dailyDataDao dd;

    public void addRawData(String ctx){
        dd.addRawData(ctx);
    }

    public void addData_M(futureData fd){
        dd.addData_M(fd);
    }


}
