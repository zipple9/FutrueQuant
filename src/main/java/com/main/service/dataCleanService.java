package com.main.service;

import com.main.dao.dataCleanDao;
import com.main.domain.FutureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wzy on 2019/2/27 23:23
 **/
@Service
public class dataCleanService {
    @Autowired
    private dataCleanDao dcd;


    List<FutureData> getRawFutureData(int limit){
        return dcd.getRawFutureData(limit);
    }
}
