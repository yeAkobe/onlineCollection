package com.zzx.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling//定时器
@SpringBootApplication
@ComponentScan("com.zzx")
@MapperScan("com.zzx.staservice.mapper")
@EnableDiscoveryClient //服务发现功能
@EnableFeignClients//服务调用-Feign
public
class staApplication {
    public static
    void main(String[] args) {
        SpringApplication.run(staApplication.class,args);
    }
}
