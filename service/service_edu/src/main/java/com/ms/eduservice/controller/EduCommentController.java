package com.ms.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.commonutils.JwtUtils;
import com.ms.commonutils.R;
import com.ms.commonutils.vo.UcenterMember;
import com.ms.eduservice.client.MemClient;
import com.ms.eduservice.entity.EduComment;
import com.ms.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-29
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;

    @Autowired
    private MemClient memClient;

    // 根据课程id获取课程评论带分页
    @GetMapping("getComment/{current}/{limit}/{courseId}")
    public R getComment(@PathVariable long current,
                        @PathVariable long limit,
                        @PathVariable String courseId) {
        Page<EduComment> commentPage = new Page<>(current, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        // 将数据封装到commentPage中
        commentService.page(commentPage, wrapper);
        List<EduComment> commentList  = commentPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", commentPage.getCurrent());
        map.put("pages", commentPage.getPages());
        map.put("size", commentPage.getSize());
        map.put("total", commentPage.getTotal());
        map.put("hasNext", commentPage.hasNext());
        map.put("hasPrevious", commentPage.hasPrevious());
        return R.ok().data(map);
    }

    // 添加评论.
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment comment,
                        HttpServletRequest request){
        // 从request的响应头获取用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(20001).message("请先登录，再使用评论功能");
        }
        comment.setMemberId(memberId);
        UcenterMember userInfo = memClient.getUserInfo(memberId);
        comment.setAvatar(userInfo.getAvatar());
        comment.setNickname(userInfo.getNickname());
        commentService.save(comment);
        return R.ok();
    }

}

