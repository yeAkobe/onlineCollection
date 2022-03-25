package com.zzx.serviceUcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient //服务发现功能
@SpringBootApplication
@ComponentScan("com.zzx")
@MapperScan("com.zzx.serviceUcenter.mapper")
public
class UcenterApplication {
    public static
    void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
