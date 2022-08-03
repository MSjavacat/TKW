package com.ms.eduservice.controller;

import com.ms.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author MS
 * @create 2022-07-18-15:58
 */
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    /**
     * 跨域问题：协议 ip 端口号，三者有一个改变，就会出现跨域问题
     * @return
     */
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2F91%2F81%2Fd7%2F9181d7d8787f7e96f0017da5e61ad27d.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660723371&t=76758bcf44448c838186ce62670c608a");
    }
}
