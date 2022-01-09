package com.aticlecollect.schedule;


import com.aticlecollect.entity.DocEntity;
import com.aticlecollect.service.EsOperationService;
import com.aticlecollect.spyder.EastMoneyPipeline;
import com.aticlecollect.spyder.EastMoneySingleStockProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.stream.Collectors;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/9/8-22:42
 */
@Component
@Slf4j
public class SingleStockArticleTask {


    @Autowired
    EsOperationService esOperationService;


    @Scheduled(cron = "0 40 8-20 * * ? ")
    public void collectArticle() {

        log.debug("start time task to collect articles");
        EastMoneyPipeline pipeline = new EastMoneyPipeline();
        Spider.create(new EastMoneySingleStockProcessor()).addUrl("http://stock.eastmoney.com/a/cggdd.html","https://finance.eastmoney.com/a/ccjdd.html")
                .addPipeline(pipeline).run();


        esOperationService.batchInsert("doc", pipeline.getDocs());
    }

}
