import com.alibaba.fastjson.JSON;
import com.aticlecollect.service.EsOperationService;
import com.aticlecollect.spyder.EastMoneySingleStockProcessor;
import com.main.BaseTest;
import com.aticlecollect.entity.DocEntity;
import com.aticlecollect.spyder.EastMoneyPageProcessor;
import com.aticlecollect.spyder.EastMoneyPipeline;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/9/7-22:58
 */
public class EsTest extends BaseTest {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private EsOperationService esOperationService;


    @Test
    public void esWriteTest(){
        EastMoneyPipeline pipeline = new EastMoneyPipeline();
        Spider.create(new EastMoneySingleStockProcessor()).addUrl("http://stock.eastmoney.com/a/cggdd.html")
                .addPipeline(pipeline).run();

        System.out.println(pipeline.getDocs().size());
        esOperationService.batchInsert("doc",pipeline.getDocs());

    }

    @Test
    public void testSearch(){
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id",
                "http://finance.eastmoney.com/a/202109102092786151.html"));

        searchSourceBuilder.fetchField("title");

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        searchResponse.getHits().getHits().toString();
    }

}
