package com.main.service;

import com.main.dao.testDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/08/08
 */
@Service
@Slf4j
public class testService {

    @Autowired
    private com.main.dao.testDao testDao;

    public void addData(){
        testDao.addData();
    }

    public void selectData(){

        log.info(testDao.selectData().toString());
    }

    public void deleteData(){
        testDao.deleteData();
    }

}
