package com.zzx.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient //服务发现功能
@EnableFeignClients//服务调用-Feign
@SpringBootApplication
@ComponentScan(basePackages = "com.zzx")//swagger用，扫描common下common-bas中src/main/java/com/zzx/servicebase/config/SwaggerConfig.java文件，在该项目下执行创建出swagger
public class Service_edu {
    public static void main(String[] args) {
        SpringApplication.run(Service_edu.class,args);
    }
}

