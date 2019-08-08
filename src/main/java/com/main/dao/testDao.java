package com.main.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/08/08
 */
@Repository
public interface testDao {


    void addData();
    List<JSONObject> selectData();
    void deleteData();
}
