package com.aticlecollect.config;


import com.aticlecollect.schedule.SingleStockArticleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2022/1/3-23:07
 */
@Service
public class AppRunnerImpl implements ApplicationRunner {


    @Autowired
    SingleStockArticleTask task;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        task.collectArticle();
    }
}
