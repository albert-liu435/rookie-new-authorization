package com.rookie.bigdata.constant;

/**
 * @Author rookie
 * @Description security 常量类
 * @Date 2024/8/14 22:04
 * @Version 1.0
 */
public class SecurityConstants {

    /**
     * 随机字符串请求头名字
     */
//    public static final String NONCE_HEADER_NAME = "nonce";

    /**
     * 随机字符串请求头名字
     */
    public static final String NONCE_HEADER_NAME = "nonceId";

    /**
     * 登录方式入参名
     */
    public static final String LOGIN_TYPE_NAME = "loginType";

    /**
     * 验证码id入参名
     */
    public static final String CAPTCHA_ID_NAME = "captchaId";

    /**
     * 验证码值入参名
     */
    public static final String CAPTCHA_CODE_NAME = "code";

    /**
     * 登录方式——短信验证码
     */
    public static final String SMS_LOGIN_TYPE = "smsCaptcha";

    /**
     * 登录方式——账号密码登录
     */
    public static final String PASSWORD_LOGIN_TYPE = "passwordLogin";

    /**
     * 权限在token中的key
     */
    public static final String AUTHORITIES_KEY = "authorities";

    /**
     * 自定义 grant type —— 短信验证码
     */
    public static final String GRANT_TYPE_SMS_CODE = "urn:ietf:params:oauth:grant-type:sms_code";

    /**
     * 自定义 grant type —— 短信验证码 —— 手机号的key
     */
    public static final String OAUTH_PARAMETER_NAME_PHONE = "phone";

    /**
     * 自定义 grant type —— 短信验证码 —— 短信验证码的key
     */
    public static final String OAUTH_PARAMETER_NAME_SMS_CAPTCHA = "sms_captcha";



    /**
     * 授权确认页面地址
     */
    public static final String LOGIN_PAGE_URI = "http://127.0.0.1:5173/login";

    /**
     * 授权确认页面地址
     */
    public static final String DEVICE_ACTIVATED_URI = "http://127.0.0.1:5173/activated";

    /**
     * 授权确认页面地址
     */
    public static final String DEVICE_ACTIVATE_URI = "http://127.0.0.1:5173/activate";

    /**
     * 授权确认页面地址
     */
    public static final String CONSENT_PAGE_URI = "http://127.0.0.1:5173/consent";

}
