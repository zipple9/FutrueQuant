package com.aticlecollect.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/9/7-22:43
 */
@Configuration
public class ESConfig {


    // 硬编码的值可以设置到配置文件，通过@Value读取
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        return client;
    }
}
