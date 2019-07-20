package com.main.dao;

import com.main.domain.FutureData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wzy
 * @Date 2019/7/4
 */
@Repository
public interface GetDataDao {



    List<FutureData> getData();
}
