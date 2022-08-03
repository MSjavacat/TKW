package com.ms.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.commonutils.R;
import com.ms.educms.entity.CrmBanner;
import com.ms.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/educms/banneradmin")
public class BannerAdminController { // 后台使用
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "分页获取banner")
    @GetMapping("getBanner/{current}/{limit}")
    public R getBanner(@PathVariable long current,
                       @PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<>(current,limit);
        bannerService.page(bannerPage,null);
        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    @ApiOperation(value = "根据id获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public R save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}

