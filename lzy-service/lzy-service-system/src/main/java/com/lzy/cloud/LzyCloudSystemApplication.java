package com.lzy.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName LzyCloudSystemApplication
 * @Description springboot 启动类
 * @Author LiZuoYang
 * @Date 2022/6/2 11:10
 **/
@EnableCaching
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.lzy.*.**.mapper.**"})
@EnableFeignClients(basePackages = {"com.lzy"})
@ComponentScan(basePackages = {"com.lzy"})
@SpringBootApplication
public class LzyCloudSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LzyCloudSystemApplication.class,args);
    }
}
