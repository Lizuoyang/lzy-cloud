package com.lzy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName LzyCloudOauthApplication
 * @Description 鉴权认证服务 启动类
 * @Author LiZuoYang
 * @Date 2022/6/23 9:53
 **/
@EnableFeignClients(basePackages = {"com.lzy"})
@ComponentScan(basePackages = {"com.lzy"})
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
@RefreshScope
public class LzyCloudOauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(LzyCloudOauthApplication.class, args);
    }
}
