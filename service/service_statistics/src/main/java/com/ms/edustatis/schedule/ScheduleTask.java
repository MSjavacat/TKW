package com.ms.edustatis.schedule;

import com.ms.edustatis.service.StatisticsDailyService;
import com.ms.edustatis.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author MS
 * @create 2022-08-01-17:06
 */
@Component
public class ScheduleTask {
    @Autowired
    private StatisticsDailyService dailyService;

    // 每天凌晨一点执行一次
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        dailyService.createStatisticsByDate(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
