package com.ms.edustatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ms.commonutils.R;
import com.ms.edustatis.client.MemClient;
import com.ms.edustatis.entity.StatisticsDaily;
import com.ms.edustatis.mapper.StatisticsDailyMapper;
import com.ms.edustatis.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-01
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private MemClient memClient;

    @Override
    public void createStatisticsByDate(String day) {
        // 先删除当前记录，再添加新记录
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        // 获取统计信息
        R r = memClient.registerCount(day);
        Integer registerNum = (Integer) r.getData().get("count");
        Integer loginNum = RandomUtils.nextInt(100,200);
        Integer videoViewNum  = RandomUtils.nextInt(100,200);
        Integer courseNum   = RandomUtils.nextInt(100,200);

        // 创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculatedList",begin,end);
        wrapper.select("date_calculatedList",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        // 日期
        List<String> date_calculatedList = new ArrayList<>();
        // 数量
        List<Integer> numDataList = new ArrayList<>();

        // 遍历staList进行封装
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            // 封装日期
            date_calculatedList.add(daily.getDateCalculated());
            // 封装数量
            switch (type){
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
