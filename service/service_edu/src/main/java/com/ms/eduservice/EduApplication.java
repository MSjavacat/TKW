package com.ms.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MS
 * @create 2022-07-13-20:25
 */
@EnableFeignClients // nacos服务调用
@EnableDiscoveryClient // nacos服务注册
@SpringBootApplication
@ComponentScan(basePackages = {"com.ms"}) // 可以把service_base中的com.ms包下的组件也扫描进来
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
