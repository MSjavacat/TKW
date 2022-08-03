package com.ms.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ms.eduservice.entity.EduSubject;
import com.ms.eduservice.entity.excel.SubjectData;
import com.ms.eduservice.entity.subject.OneSubject;
import com.ms.eduservice.entity.subject.TwoSubject;
import com.ms.eduservice.listener.SubjectExcelListener;
import com.ms.eduservice.mapper.EduSubjectMapper;
import com.ms.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-07-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 添加课程分类
    @Override
    public void saveObject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 显示课程分类列表(树形)
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 查询所有一级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper1);

        // 查询所有二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapper2);

        // 返回结果
        List<OneSubject> finalSubjectList = new ArrayList<>();

        HashMap<String, OneSubject> OneMap = new HashMap<>();

        // 封装一级分类
        for(EduSubject eduSubject : oneSubjectList){
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);
            OneMap.put(oneSubject.getId(),oneSubject);
        }

        // 封装二级分类
        for(EduSubject twoSubject : twoSubjectList){
            OneSubject oneSubject = OneMap.get(twoSubject.getParentId());
            TwoSubject realTwoSubject = new TwoSubject();
            BeanUtils.copyProperties(twoSubject,realTwoSubject);
            oneSubject.getChildren().add(realTwoSubject);
        }

        return finalSubjectList;
    }
}
