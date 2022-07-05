package com.qzh.articleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
@ComponentScan("com.qzh")
public class ArticleAppliaction {
    public static void main(String[] args) {
        SpringApplication.run(ArticleAppliaction.class);
    }
}
