package com.alibaba.spring_es;

import com.alibaba.spring_es.config.ElasticSearchConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEsApplication.class, args);
    }

}
