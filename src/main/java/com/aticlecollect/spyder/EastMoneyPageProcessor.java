package com.aticlecollect.spyder;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/8/7-16:14
 */
@Slf4j
public class EastMoneyPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(http://finance\\.eastmoney\\.com/a/202108072035272835\\.html)").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("content", page.getHtml().xpath("//div[@class='title']").toString());
        page.putField("title", page.getHtml().$("div.title","text").toString());
        String content = page.getHtml().$("div.txtinfos").all().toString()
                .replaceAll("(<.*?>)","") .replaceAll("\\n","");
        content=content.substring(1,content.length()-1);
        content=StringUtils.trimAllWhitespace(content);


        page.putField("content", content);
//        log.info(content);
//        log.info(content.substring(0,1));



        // 空的 div下没有text
//        page.putField("content3", page.getHtml().$("div.txtinfos","text").all().toString());
//                page.putField("content", page.getHtml().$("div.txtinfos").$("p").all().toString()
//        .replaceAll("<p>",""));



        //        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        EastMoneyPipeline pipeline = new EastMoneyPipeline();
        Spider.create(new EastMoneyPageProcessor()).addUrl("http://finance.eastmoney.com/a/202108072035272835.html")
                .addPipeline(pipeline).run();

        System.out.println(pipeline);
    }

}
