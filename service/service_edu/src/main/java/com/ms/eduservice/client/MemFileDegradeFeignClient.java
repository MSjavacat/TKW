package com.ms.eduservice.client;

import com.ms.commonutils.vo.UcenterMember;
import org.springframework.stereotype.Component;

/**
 * @author MS
 * @create 2022-07-29-19:55
 */
@Component
public class MemFileDegradeFeignClient implements MemClient {
    @Override
    public UcenterMember getUserInfo(String id) {
        return null;
    }
}
