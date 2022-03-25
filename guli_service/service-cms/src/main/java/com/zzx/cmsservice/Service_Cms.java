package com.zzx.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.zzx")
@MapperScan("com.zzx.cmsservice.mapper")
@EnableDiscoveryClient
public
class Service_Cms {
    public static
    void main(String[] args) {
        SpringApplication.run(Service_Cms.class,args);
    }
}
