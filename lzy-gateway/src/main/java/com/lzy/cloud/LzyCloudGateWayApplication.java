package com.lzy.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName LzyCloudGateWayApplication
 * @Description 网关服务 启动类
 * @Author LiZuoYang
 * @Date 2022/6/16 14:00
 **/
@Slf4j
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.lzy"})
@SpringBootApplication
@EnableCaching
public class LzyCloudGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LzyCloudGateWayApplication.class, args);
    }
}
