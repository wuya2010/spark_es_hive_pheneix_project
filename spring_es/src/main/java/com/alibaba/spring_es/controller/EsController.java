package com.alibaba.spring_es.controller;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 添加描述</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: TY</p>
 *
 * @author kylin
 * @version 1.0
 * @date 2021/5/25 19:07
 */

@RestController //这种方式比较死板： = @Controller + @ResponseBody
@RequestMapping("/search")
public class EsController {

    // 请求地址： localhost:8111/search/test


    @Autowired
    @Qualifier("client")
    private RestHighLevelClient client;


    // 查询接口： http://localhost:8080/search/test
    @RequestMapping("/test")
    void contextLoads() throws IOException {
        //1、创建查询请求，规定查询的索引
        SearchRequest request = new SearchRequest("dw_gofish_company");

        //2、创建条件构造 : Constructs a new search source builder
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //3、构造条件
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("country", "美国");
        builder.query(matchQueryBuilder);

//        //聚合年龄
//        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("bing_status");
//        builder.aggregation(ageAgg);
//
//        //聚合平均年龄
//        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("bing_status");
//        builder.aggregation(balanceAvg);


        //4、将构造好的条件放入请求中
        request.source(builder);

        //5、开始执行发送request请求
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);

        //6、开始处理返回的数据， 返回数据
        SearchHit[] hits = searchResponse.getHits().getHits();

        List<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            String hitString = hit.getSourceAsString();
            System.out.println(hitString);
            list.add(hitString);
        }

    }



    /*
    GET /bank/_search
        {
          "query": {
            "match": {
              "address": "Lane"
            }
          },
          "aggs": {
            "ageAgg": {
              "terms": {
                "field": "age"
              }
            },
            "balanceAvg":{
              "avg": {
                "field": "balance"
              }
            }
          }
        }

     */
}
