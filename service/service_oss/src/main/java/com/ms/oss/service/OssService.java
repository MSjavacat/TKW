package com.ms.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author MS
 * @create 2022-07-19-17:47
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
