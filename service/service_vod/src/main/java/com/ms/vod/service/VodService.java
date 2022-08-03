package com.ms.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author MS
 * @create 2022-07-23-11:22
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeAllVideos(List<String> videoList);
}
