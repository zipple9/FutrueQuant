package com.main.dao;


import com.main.domain.FutureData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryDataDao {

    int addHistroyData(FutureData fd);
    List<FutureData> getHistoryData();
    List<FutureData> getData(Integer limit,String startDate,String endDate);


}
