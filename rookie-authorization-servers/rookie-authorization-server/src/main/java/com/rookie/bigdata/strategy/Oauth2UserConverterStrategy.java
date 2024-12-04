package com.rookie.bigdata.strategy;

import com.rookie.bigdata.entity.Oauth2ThirdAccount;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @Class Oauth2UserConverterStrategy
 * @Description  oauth2 三方登录获取到的用户信息转换策略接口
 * @Author rookie
 * @Date 2024/12/4 18:06
 * @Version 1.0
 */
public interface Oauth2UserConverterStrategy {

    /**
     * 将oauth2登录的认证信息转为 {@link Oauth2ThirdAccount}
     *
     * @param oAuth2User oauth2登录获取的用户信息
     * @return 项目中的用户信息
     */
    Oauth2ThirdAccount convert(OAuth2User oAuth2User);

}

