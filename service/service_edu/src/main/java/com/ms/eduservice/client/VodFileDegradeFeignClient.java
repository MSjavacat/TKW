package com.ms.eduservice.client;

import com.ms.commonutils.R;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author MS
 * @create 2022-07-24-19:00
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频出错");
    }
}
