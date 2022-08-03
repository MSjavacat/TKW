package com.ms.eduorder.service;

import com.ms.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-31
 */
public interface PayLogService extends IService<PayLog> {

    // 生成订单二维码
    Map createNative(String orderNo);

    // 查询订单状态
    Map<String, String> queryPayStatus(String orderNo);

    // 更新订单表
    void updateOrdersStatus(Map<String, String> map);
}
