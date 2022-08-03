package com.ms.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.commonutils.R;
import com.ms.eduservice.entity.EduTeacher;
import com.ms.eduservice.entity.vo.TeacherQuery;
import com.ms.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-13
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 1.查询所有讲师 restful风格
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> eduTeacherList = eduTeacherService.list(null);
        R result = R.ok().data("eduTeacherList", eduTeacherList);
        return result;
     }

    /**
     * 2.逻辑删除讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)
                                 @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);

        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 3.分页查询讲师
     * @param current
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current",value = "当前页",required = true) @PathVariable long current,
                             @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit){
        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 调用方法实现分页，把分页所有数据封装到pageTeacher中
        eduTeacherService.page(pageTeacher, null);

        // 获取总记录数
        long total = pageTeacher.getTotal();
        
        // 获取记录
        List<EduTeacher> records = pageTeacher.getRecords();

        Map<String, Object> map = new HashMap<>();

        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }


    /**
     * 4.条件查询讲师
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "条件查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            // 构建条件
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)) {
            // ge:>=
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)) {
            // le:<=
            wrapper.le("gmt_create",end);
        }

        // 排序
        wrapper.orderByDesc("gmt_create");

        // 调用方法实现分页查询，封装到pageTeacher中
        eduTeacherService.page(pageTeacher,wrapper);

        // 获取总记录数
        long total = pageTeacher.getTotal();

        // 获取记录
        List<EduTeacher> records = pageTeacher.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    /**
     * 5.添加讲师
     * @return
     */
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R saveTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 6.根据id查询讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    /**
     * 7.修改讲师
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

