package com.ms.educenter.service;

import com.ms.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterByDay(String day);
}
