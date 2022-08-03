package com.ms.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ms.commonutils.R;
import com.ms.servicebase.exceptionhandler.EduException;
import com.ms.vod.service.VodService;
import com.ms.vod.utils.ConstantPropertiesUtil;
import com.ms.vod.utils.InitObject;
import com.ms.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author MS
 * @create 2022-07-23-11:21
 */
@RestController
@RequestMapping("eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    // 上传视频
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    // 删除单个视频
    @DeleteMapping("removeVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            // 实现删除
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(20001,"删除视频失败");
        }
        return R.ok();
    }

    // 删除多个视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList){
        vodService.removeAllVideos(videoList);
        return R.ok();
    }

    // 获取播放凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            // 根据视频id获取视频 播放凭证
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(id);

            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();

            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new EduException(20001,"获取凭证失败");
        }
    }
}
