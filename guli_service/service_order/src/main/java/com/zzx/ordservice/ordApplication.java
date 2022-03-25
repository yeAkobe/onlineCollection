package com.zzx.ordservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.zzx.ordservice.mapper")
@ComponentScan("com.zzx")
@EnableDiscoveryClient //服务发现功能
@EnableFeignClients//服务调用-Feign
public
class ordApplication {
    public static
    void main(String[] args) {
        SpringApplication.run(ordApplication.class,args);
    }
}
