package com.main.dao;

import com.main.domain.FutureData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wzy on 2019/2/27 23:24
 **/
@Repository
public interface dataCleanDao {


    List<FutureData> getRawFutureData(@Param("limit")int limit);


}
