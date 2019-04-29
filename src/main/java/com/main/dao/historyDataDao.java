package com.main.dao;


import com.main.domain.FutureData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface historyDataDao {

    int addHistroyData(FutureData fd);
    List<FutureData> getHistoryData();

}
