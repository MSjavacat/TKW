package com.ms.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ms.commonutils.JwtUtils;
import com.ms.commonutils.MD5;
import com.ms.educenter.entity.UcenterMember;
import com.ms.educenter.entity.vo.RegisterVo;
import com.ms.educenter.mapper.UcenterMemberMapper;
import com.ms.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.servicebase.exceptionhandler.EduException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-07-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {
        // 判断输入值是否为空
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            // 如果有一个为空，抛出异常
            throw new EduException(20001,"手机号或者密码不能为空");
        }

        // 两个值不为空
        // 根据手机号去查询数据库,得到用户对象
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        // 判断用户是否为空
        if (ucenterMember == null) {
            throw new EduException(20001,"用户不存在");
        }

        // 用户不为空，判断密码是否正确
        // 对密码进行加密
        String md5Password = MD5.encrypt(password);
        if (!md5Password.equals(ucenterMember.getPassword())) {
            throw new EduException(20001,"密码不正确");
        }

        // 判断用户是否被禁用
        if (ucenterMember.getIsDisabled()) {
            throw new EduException(20001,"该用户已被禁止登录");
        }

        // 电话号码存在，密码也正确，则生成token
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        // 获取前端传来数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickName = registerVo.getNickName();
        String password = registerVo.getPassword();

        // 判断是否为空
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(nickName) || StringUtils.isEmpty(password)) {
            throw new EduException(20001,"不能输入空值");
        }

        // 判断验证码是否正确
        if (!redisTemplate.opsForValue().get(mobile).equals(code)) {
            throw new EduException(20001,"验证码错误");
        }

        // 判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count != 0) {
            throw new EduException(20001,"手机号码重复");
        }

        // 注册该用户
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickName);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
