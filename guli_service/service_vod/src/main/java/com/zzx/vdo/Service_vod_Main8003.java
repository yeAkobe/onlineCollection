package com.zzx.vdo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient //服务发现功能
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.zzx"}) //用于扫描swagger
public class Service_vod_Main8003 {
    public static void main(String[] args) {
        SpringApplication.run(Service_vod_Main8003.class,args);
    }
}
