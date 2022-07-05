package com.qzh.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *@Author: qzh
 *@Date: 2022/4/12 17:05
 */

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.qzh.educenter.mapper")
@EnableSwagger2
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
