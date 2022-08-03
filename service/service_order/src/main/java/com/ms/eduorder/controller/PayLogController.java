package com.ms.eduorder.controller;


import com.ms.commonutils.R;
import com.ms.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-31
 */
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    // 生成微信二维码接口，根据课程id
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        // 返回信息，包括二维码地址，还有其他信息
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    // 查询订单状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            // 添加记录到记录表，更新订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中").code(25000);
    }
}

