package com.main.dao;


import com.main.domain.futureData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface historyDataDao {

    int addHistroyData(futureData fd);
    List<futureData> getHistoryData();

}
