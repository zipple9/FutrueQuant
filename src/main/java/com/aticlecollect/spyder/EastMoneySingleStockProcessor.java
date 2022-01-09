package com.aticlecollect.spyder;


import com.aticlecollect.entity.DocEntity;
import com.aticlecollect.service.EsOperationService;
import com.commonbase.util.CommonUtils;
import com.commonbase.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/8/7-16:14
 */
@Slf4j
public class EastMoneySingleStockProcessor implements PageProcessor {

    private Site site = Site.me().setTimeOut(400).setRetryTimes(3).setSleepTime(100);

//    private Map<String,String> datas = new HashMap<>();

    @Autowired
    private EsOperationService esOperationService;

    @Override
    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(http://finance\\.eastmoney\\.com/a/202108072035272835\\.html)").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("content", page.getHtml().xpath("//div[@class='title']").toString());

//        page.getHtml().links().$("div.guidance_list").all();
//        page.putField("url", page.getHtml().$("div.guidance_list").toString());

//        List<String> urls = page.getHtml().links().regex("http://finance.eastmoney.com/a/.*").all().stream().distinct().collect(Collectors.toList());
        List<String> urls = page.getHtml().$("ul#newsListContent a", "href").all().stream().distinct().collect(Collectors.toList());
//        log.debug(urls.toString());

        if(CollectionUtils.isNotEmpty(urls)){
            // 首个页面不收集数据
            page.setSkip(true);
            EsOperationService esOperationService = SpringBeanUtils.getBean(EsOperationService.class);
            List<String> addUrls = esOperationService.queryArticleExist(urls);
            page.addTargetRequests(addUrls);
            log.debug("{} urls need to be collected",addUrls.size());
        }else {
            String title = page.getHtml().$("div.title", "text").toString();
            String publishDate = page.getHtml().$("div.infos").$("div.item","text").toString();
            String content = page.getHtml().$("div.txtinfos").all().toString()
                    .replaceAll("(<.*?>)", "").replaceAll("\\n", "");
            content = content.substring(1, content.length() - 1);
            content = StringUtils.trimAllWhitespace(content);
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(content)){
                DocEntity doc = new DocEntity();

                doc.setDocId(CommonUtils.generateUUID());
                doc.setTitle(title);
                doc.setContent(content);
                doc.setCollectTime(LocalDateTime.now());
                doc.setUrl(page.getUrl().toString());
                try {
                    doc.setPublishDate(LocalDateTime.parse(publishDate, DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm")));
                } catch (Exception e) {
                    log.warn("spider page {} publish date failed. publish date we get is {}",page.getUrl(),publishDate);
                }
                page.putField("data", doc);
            }

        }


//        if (page.getTargetRequests().size() > 0) {
//
//        }
        //        log.info(page.getUrl().toString());



//        log.debug(page.getResultItems().getAll().toString());

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        EastMoneyPipeline pipeline = new EastMoneyPipeline();

        Spider.create(new EastMoneySingleStockProcessor()).addUrl("https://finance.eastmoney.com/a/ccjdd.html")
                .addPipeline(pipeline).run();

        log.debug(pipeline.getDocs().stream().collect(Collectors.toMap(DocEntity::getUrl, DocEntity::getTitle)).toString());
    }

}
