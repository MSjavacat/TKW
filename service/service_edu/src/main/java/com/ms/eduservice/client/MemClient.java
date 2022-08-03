package com.ms.eduservice.client;

import com.ms.commonutils.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @author MS
 * @create 2022-07-29-19:54
 */
@Component
@FeignClient(value = "service-ucenter",fallback = MemFileDegradeFeignClient.class)
public interface MemClient {

    @PostMapping("/educenter/member/getUserInfo/{id}")
    public UcenterMember getUserInfo(@PathVariable("id") String id);
}
