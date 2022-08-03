package com.ms.educms.controller;

import com.ms.commonutils.R;
import com.ms.educms.entity.CrmBanner;
import com.ms.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MS
 * @create 2022-07-25-21:38
 */
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController { // 前台使用

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}
