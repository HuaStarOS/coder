package com.qzh.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author qzh
 */
@SpringBootApplication
@EnableDiscoveryClient //服务发现   新版可以不写了，需要在配置文件中配置nacos即可
public class GateWallMain8222 {
    public static void main(String[] args) {
        SpringApplication.run(GateWallMain8222.class,args);
    }
}
