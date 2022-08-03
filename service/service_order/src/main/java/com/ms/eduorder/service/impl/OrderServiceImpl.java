package com.ms.eduorder.service.impl;

import com.ms.commonutils.vo.CourseWebVoOrder;
import com.ms.commonutils.vo.UcenterMemberOrder;
import com.ms.eduorder.client.EduClient;
import com.ms.eduorder.client.UcenterClient;
import com.ms.eduorder.entity.Order;
import com.ms.eduorder.mapper.OrderMapper;
import com.ms.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-07-31
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String memberId) {
        // 通过远程调用实现
        // 获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        // 获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        // 取出值加入到数据库
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); // 订单id
        order.setCourseId(courseId); // 课程id
        order.setCourseTitle(courseInfoOrder.getTitle()); // 课程名称
        order.setCourseCover(courseInfoOrder.getCover()); // 课程封面
        order.setTeacherName(courseInfoOrder.getTeacherName()); // 课程讲师
        order.setMemberId(userInfoOrder.getId()); // 用户id
        order.setNickname(userInfoOrder.getNickname()); //用户名称
        order.setMobile(userInfoOrder.getMobile()); //用户手机
        order.setStatus(0); // 未支付状态
        order.setPayType(1); // 微信支付

        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
