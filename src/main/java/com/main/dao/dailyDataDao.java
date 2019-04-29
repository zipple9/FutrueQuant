package com.main.dao;

import com.main.domain.FutureData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wzy on 2019/1/19 14:07
 **/
@Repository
public interface dailyDataDao {

    int addRawData(@Param("ctx")String ctx);
    int addData_M(FutureData fd);
    List<FutureData> getData();
}
