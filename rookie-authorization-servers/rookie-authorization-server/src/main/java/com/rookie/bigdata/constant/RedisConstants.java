package com.rookie.bigdata.constant;

/**
 * @Class RedisConstants
 * @Description Redis相关常量
 * @Author rookie
 * @Date 2024/4/10 9:18
 * @Version 1.0
 */
public class RedisConstants {

    /**
     * 认证信息存储前缀
     */
    public static final String SECURITY_CONTEXT_PREFIX_KEY = "security_context:";

    /**
     * 短信验证码前缀
     */
    public static final String SMS_CAPTCHA_PREFIX_KEY = "mobile_phone:";

    /**
     * 图形验证码前缀
     */
    public static final String IMAGE_CAPTCHA_PREFIX_KEY = "image_captcha:";

    /**
     * 默认过期时间，默认五分钟
     */
    public static final long DEFAULT_TIMEOUT_SECONDS = 60L * 5;

}
