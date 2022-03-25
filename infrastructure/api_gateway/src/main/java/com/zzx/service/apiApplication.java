package com.zzx.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public
class apiApplication {
    public static
    void main(String[] args) {
        SpringApplication.run(apiApplication.class,args);
    }
}
