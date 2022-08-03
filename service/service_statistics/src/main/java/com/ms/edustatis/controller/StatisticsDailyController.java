package com.ms.edustatis.controller;


import com.ms.commonutils.R;
import com.ms.edustatis.client.MemClient;
import com.ms.edustatis.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-01
 */
@RestController
@RequestMapping("/edustatis/statistics")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    // 统计某一天的注册人数，并加到数据库中
    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day){
        dailyService.createStatisticsByDate(day);
        return R.ok();
    }

    @GetMapping("showchart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }
}

