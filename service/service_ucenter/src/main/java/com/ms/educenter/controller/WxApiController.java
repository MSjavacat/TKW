package com.ms.educenter.controller;

import com.google.gson.Gson;
import com.ms.commonutils.JwtUtils;
import com.ms.educenter.entity.UcenterMember;
import com.ms.educenter.service.UcenterMemberService;
import com.ms.educenter.utils.ConstantWxUtils;
import com.ms.educenter.utils.HttpClientUtils;
import com.ms.servicebase.exceptionhandler.EduException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author MS
 * @create 2022-07-28-11:16
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("callback")
    public String callback(String code, String state){
        try {
            // 获取code值，临时票据，类似于验证码

            // 1.拿着code去请求微信固定地址，得到两个值access_token,和openId
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            // 请求这个拼接的路径，得到返回的两个值
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            // 从该json字符串获取信息
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            // 把扫码人信息添加到数据库
            // 判断是否存在扫码人信息,存在不添加
            UcenterMember member = memberService.getOpenIdMember(openid);

            if (member == null) {
                // 再去访问微信固定地址得到用户信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        accessToken,
                        openid
                );

                String userInfo = HttpClientUtils.get(userInfoUrl);

                System.out.println(userInfo);

                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                // 昵称
                String nickName = (String) userInfoMap.get("nickname");
                // 头像
                String headimgurl = (String) userInfoMap.get("headimgurl");

                // 添加
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickName);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }

            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            // 回到首页面
            return "redirect:http://localhost:3000?token="+jwtToken;

        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(20001,"登录失败");
        }
    }

    @GetMapping("login")
    public String getWxCode(){
        // String url = "https://open.weixin.qq.com/connect/qrconnect?appId="+ ConstantWxUtils.WX_OPEN_APP_ID;
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "ms"
        );

        // 请求微信地址
        return "redirect:" + url;
    }
}
