package com.ms.eduservice.service;

import com.ms.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-19
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveObject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
