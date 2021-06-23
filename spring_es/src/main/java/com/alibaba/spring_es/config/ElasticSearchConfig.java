package com.alibaba.spring_es.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 添加描述</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: TY</p>
 *
 * @author kylin
 * @version 1.0
 * @date 2021/5/25 18:52
 */

@Configuration //该类等价 与XML中配置beans，相当于Ioc容器
public class ElasticSearchConfig {

    @Bean("client") //它的某个方法头上如果注册了@Bean，就会作为这个Spring容器中的Bean，与xml中配置的bean意思一样
    public RestHighLevelClient client(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                //生成环境下ip
                new HttpHost("192.168.18.151", 19200, "http"),
                new HttpHost("192.168.18.149", 19200, "http"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        BasicCredentialsProvider baseProvider = new BasicCredentialsProvider();
                        baseProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic", "H84I4fw6fDgdenuNRgfe"));
                        HttpAsyncClientBuilder httpAsyncClientBuilder = httpClientBuilder.setDefaultCredentialsProvider(baseProvider);
                        return httpAsyncClientBuilder;
                    }
                }));
        return client;
    }

}
