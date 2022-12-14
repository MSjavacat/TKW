package com.ms.eduorder.service;

import com.ms.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-31
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberId);
}
