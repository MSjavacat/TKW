package com.ms.educenter.controller;


import com.ms.commonutils.JwtUtils;
import com.ms.commonutils.R;
import com.ms.commonutils.vo.UcenterMemberOrder;
import com.ms.educenter.entity.UcenterMember;
import com.ms.educenter.entity.vo.RegisterVo;
import com.ms.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-27
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    // 登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    // 注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    // 根据token获取用户信息，用于前端页面显示
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        // 获取用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    // 获取用户评论所需的基本信息
    @PostMapping("getUserInfo/{id}")
    public UcenterMember getUserInfo(@PathVariable String id){
        UcenterMember ucenterMember = memberService.getById(id);
        return ucenterMember;
    }

    // 根据用户id获取用户订单信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,memberOrder);
        return memberOrder;
    }

    // 根据日期查询当天注册人数
    @GetMapping("countRegister/{day}")
    public R registerCount(@PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("count",count);
    }
}

