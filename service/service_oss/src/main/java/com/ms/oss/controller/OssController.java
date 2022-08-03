package com.ms.oss.controller;

import com.ms.commonutils.R;
import com.ms.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author MS
 * @create 2022-07-19-17:48
 */
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    private OssService ossService;

    // 上传头像方法
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        // 返回oss路径
        String url = ossService.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }
}
