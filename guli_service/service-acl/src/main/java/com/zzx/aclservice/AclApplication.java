package com.zzx.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.zzx")
@MapperScan("com.zzx.aclservice.mapper")
@EnableDiscoveryClient
public
class AclApplication {
    public static
    void main(String[] args) {
        SpringApplication.run(AclApplication.class,args);
    }
}
