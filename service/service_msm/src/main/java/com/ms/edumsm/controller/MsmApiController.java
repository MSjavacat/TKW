package com.ms.edumsm.controller;

import com.ms.commonutils.R;
import com.ms.edumsm.service.MsmService;
import com.ms.edumsm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author MS
 * @create 2022-07-26-18:10
 */
@RestController
@RequestMapping("/edumsm/msm")
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        // 根据手机号获取验证码判断是否过期
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) { // 验证码不为空
            return R.ok();
        }
        // 生成验证码
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean flag = msmService.send(code, phone);
        if (flag) {
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }

}
