package com.ms.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author MS
 * @create 2022-07-23-11:29
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.ACCESS_KEY_ID = keyid;
        this.ACCESS_KEY_SECRET = keysecret;
    }
}
