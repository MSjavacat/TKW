package com.ms.edumsm.service.impl;

import com.ms.edumsm.service.MsmService;
import com.ms.edumsm.utils.ConstantUtil;
import com.ms.edumsm.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MS
 * @create 2022-07-26-18:10
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String code, String phone) {
        String host = ConstantUtil.HOST;
        String path = ConstantUtil.PATH;
        String method = ConstantUtil.METHOD;
        String appcode = ConstantUtil.APPCODE;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:" + code + ",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "a09602b817fd47e59e7c6e603d3f088d");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            System.out.println(response.toString());
            return true;
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
