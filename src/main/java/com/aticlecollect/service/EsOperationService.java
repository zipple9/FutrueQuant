package com.aticlecollect.service;


import com.alibaba.fastjson.JSON;
import com.aticlecollect.entity.DocEntity;
import com.commonbase.util.WCollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/9/8-22:50
 */
@Service
@Slf4j
public class EsOperationService {

    @Autowired
    private RestHighLevelClient client;

    public void queryEs() {


    }

    public <T> void checkRepetition(String index, List<T> dataList) {

        BulkRequest request = new BulkRequest(index);

//        client.bulk();

    }


    public List<String> queryArticleExist(List<String> ids){


        // 查询已经存在的文章
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id",
                ids));

        searchSourceBuilder.fetchField("title").size(ids.size());

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        List<String> existIds = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            existIds.add(hit.getId());
        }
        return ids.stream().filter(i->!existIds.contains(i)).collect(Collectors.toList());
    }

    public void batchInsert(String index, List<DocEntity> dataList) {

        log.debug("Add article size is {}",dataList.size());
        if(CollectionUtils.isEmpty(dataList)){
            return;
        }
        // 创建请求
        BulkRequest request = new BulkRequest(index);
        // 规则 put /kuang_index/_doc/1

        dataList.forEach(data -> {
            request.add(new IndexRequest(index).source(JSON.toJSONString(data), XContentType.JSON).id(data.getUrl()));
        });
//        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("5s");
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);


        // 客户端发送请求 , 获取响应的结果
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BulkItemResponse[] items = bulkResponse.getItems();
        for (BulkItemResponse item : items) {
            log.debug("doc id is {}, status is {}", item.getId(), item.status());
        }

        try {
            // 完成后须要关闭客户端
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
