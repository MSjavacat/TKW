package com.ms.eduservice.controller;


import com.ms.commonutils.R;
import com.ms.eduservice.entity.subject.OneSubject;
import com.ms.eduservice.entity.subject.TwoSubject;
import com.ms.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    // 添加课程分类
    // 获取上传的文件，把文件内容进行读取
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        // 上传过来excel文件
        eduSubjectService.saveObject(file,eduSubjectService);
        return R.ok();
    }

    // 获取课程分类列表(树形)
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

