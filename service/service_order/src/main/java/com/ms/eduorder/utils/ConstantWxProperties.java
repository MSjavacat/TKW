package com.ms.eduorder.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author MS
 * @create 2022-07-31-15:33
 */
@Data
@Component
public class ConstantWxProperties implements InitializingBean {

    @Value("${wx.pay.appid}")
    private String appid;
    @Value("${wx.pay.partner}")
    private String partner;
    @Value("${wx.pay.partnerkey}")
    private String partnerkey;
    @Value("${wx.pay.notifyurl}")
    private String notifyurl;

    public static String APP_ID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appid;
        PARTNER = this.partner;
        PARTNER_KEY = this.partnerkey;
        NOTIFY_URL = this.notifyurl;
    }
}
