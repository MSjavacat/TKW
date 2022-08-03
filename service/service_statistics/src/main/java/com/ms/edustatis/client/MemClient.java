package com.ms.edustatis.client;

import com.ms.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author MS
 * @create 2022-08-01-15:51
 */
@Component
@FeignClient("service-ucenter")
public interface MemClient {

    // 根据日期查询当天注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public R registerCount(@PathVariable String day);
}
