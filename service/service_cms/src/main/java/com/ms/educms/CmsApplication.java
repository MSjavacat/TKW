package com.ms.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MS
 * @create 2022-07-25-21:27
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ms"})
@MapperScan("com.ms.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
