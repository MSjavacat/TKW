package com.ms.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MS
 * @create 2022-07-31-9:58
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ms"})
@MapperScan("com.ms.eduorder.mapper")
@EnableDiscoveryClient // 服务注册
@EnableFeignClients // 表示该模块要进行远程调用
public class OrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class,args);
    }
}
