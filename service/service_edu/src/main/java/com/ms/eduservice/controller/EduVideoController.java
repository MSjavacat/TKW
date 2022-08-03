package com.ms.eduservice.controller;


import com.ms.commonutils.R;
import com.ms.eduservice.client.VodClient;
import com.ms.eduservice.entity.EduVideo;
import com.ms.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-20
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    // 添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    // 删除小节
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        // 根据小节id获得视频id
        EduVideo eduVideo = videoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        // 根据视频id实现远程调用删除视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.deleteVideo(videoSourceId);
        }
        // 删除小节
        videoService.removeById(videoId);
        return R.ok();
    }

    // 修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    // 获取小节
    @GetMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }
}

