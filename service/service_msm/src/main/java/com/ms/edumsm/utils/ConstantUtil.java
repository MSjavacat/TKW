package com.ms.edumsm.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author MS
 * @create 2022-07-26-20:36
 */
@Component
@Data
public class ConstantUtil implements InitializingBean {

    @Value("${aliyun.message.host}")
    private String host;

    @Value("${aliyun.message.path}")
    private String path;

    @Value("${aliyun.message.method}")
    private String method;

    @Value("${aliyun.message.appcode}")
    private String appcode;

    public static String HOST;
    public static String PATH;
    public static String METHOD;
    public static String APPCODE;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = this.host;
        PATH = this.path;
        METHOD = this.method;
        APPCODE = this.appcode;
    }
}
