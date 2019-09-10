package com.main.service;

import com.main.dao.HistoryDataDao;
import com.main.domain.FutureData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wzy
 * @Date 2019/7/4
 */
@Service
@Slf4j
public class GetDataService {

    @Autowired
    HistoryDataDao historyDataDao;


    public List<FutureData> getData(Integer limit,String startDate,String endDate){
        return historyDataDao.getData(limit,startDate,endDate);
    }
}
