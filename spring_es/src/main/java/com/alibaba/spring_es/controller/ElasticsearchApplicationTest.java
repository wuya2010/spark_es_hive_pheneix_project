package com.alibaba.spring_es.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.spring_es.pojo.Person;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * <p>Description: 添加描述</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: TY</p>
 *
 * @author kylin
 * @version 1.0
 * @date 2021/5/26 11:58
 */

@SpringBootTest
public class ElasticsearchApplicationTest {

    /**
     * 测试类暂时不能用
     */

    @Autowired
    private RestHighLevelClient client;

    //创建索引
    @Test
    void createIndex() throws IOException {
        CreateIndexRequest index_wang = new CreateIndexRequest("dw_test_wang");
        client.indices().create(index_wang, RequestOptions.DEFAULT);
    }

    //判断索引是否存在
    @Test
    void getIndex() throws IOException {
        GetIndexRequest index_wang = new GetIndexRequest("dw_test_wang");
        boolean is_exist = client.indices().exists(index_wang, RequestOptions.DEFAULT);
        System.out.println(is_exist);
    }

    //删除索引
    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest index_wang = new DeleteIndexRequest("dw_test_wang");
        AcknowledgedResponse delete = client.indices().delete(index_wang, RequestOptions.DEFAULT);
        // Returns whether the response is acknowledged or not
        System.out.println(delete.isAcknowledged());
    }

    //增加信息
    @Test
    void addDocument() throws IOException {
        Person wang = new Person("wang", 1);
        IndexRequest index_wang = new IndexRequest("dw_test_wang");
        index_wang.id("1001");
        index_wang.timeout(TimeValue.timeValueSeconds(1));
        index_wang.source(JSON.toJSONString(wang), XContentType.JSON);
        IndexResponse response = client.index(index_wang, RequestOptions.DEFAULT);

        System.out.println(response.toString());
        System.out.println(response.status());
    }

    //是否存在
    @Test
    void exitDocument() throws IOException {
        GetRequest index_wang = new GetRequest("dw_test_wang","1");
        //不返回source的上写文
        index_wang.fetchSourceContext(new FetchSourceContext(false));
        index_wang.storedFields("_none_");

        //判断是否存在
        boolean is_exist = client.exists(index_wang, RequestOptions.DEFAULT);
        System.out.println(is_exist);
    }

    //批量查询，批量删除等


}
