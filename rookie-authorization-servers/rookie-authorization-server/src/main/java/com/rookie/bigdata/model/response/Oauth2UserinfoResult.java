package com.rookie.bigdata.model.response;

import com.rookie.bigdata.model.security.CustomGrantedAuthority;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @Class Oauth2UserinfoResult
 * @Description oauth2登录后获取用户信息响应类
 * @Author rookie
 * @Date 2024/12/4 18:01
 * @Version 1.0
 */
@Data
public class Oauth2UserinfoResult {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 用户账号
     */
    private String sub;

    /**
     * 用户名、昵称
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 用户来源
     */
    private String sourceFrom;

    /**
     * 权限信息
     */
    private Collection<CustomGrantedAuthority> authorities;

    /**
     * 地址
     */
    private String location;

    /**
     * 三方登录用户名
     */
    private String thirdUsername;

    /**
     * 三方登录获取的认证信息
     */
    private String credentials;

    /**
     * 三方登录获取的认证信息的过期时间
     */
    private LocalDateTime credentialsExpiresAt;

}
