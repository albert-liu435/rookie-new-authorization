package com.rookie.bigdata.authorization.sms;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken;

import java.util.*;


/**
 * @Author rookie
 * @Description 自定义短信验证登录Token类 可以参考 {@link OAuth2AuthorizationCodeAuthenticationToken}
 * @Date 2024/4/9 21:54
 * @Version 1.0
 */
public class SmsCaptchaGrantAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 本次登录申请的scope
     */
    private final Set<String> scopes;

    /**
     * 客户端认证信息
     */
    private final Authentication clientPrincipal;

    /**
     * 当前请求的参数
     */
    private final Map<String, Object> additionalParameters;

    /**
     * 认证方式
     */
    private final AuthorizationGrantType authorizationGrantType;

    public SmsCaptchaGrantAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                              Authentication clientPrincipal,
                                              @Nullable Set<String> scopes,
                                              @Nullable Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        this.scopes = scopes;
        this.clientPrincipal = clientPrincipal;
        this.additionalParameters = additionalParameters;
        this.authorizationGrantType = authorizationGrantType;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return clientPrincipal;
    }

    /**
     * 返回请求的scope(s)
     *
     * @return 请求的scope(s)
     */
    public Set<String> getScopes() {
        return this.scopes;
    }

    /**
     * 返回请求中的authorization grant type
     *
     * @return authorization grant type
     */
    public AuthorizationGrantType getAuthorizationGrantType() {
        return this.authorizationGrantType;
    }

    /**
     * 返回请求中的附加参数
     *
     * @return 附加参数
     */
    public Map<String, Object> getAdditionalParameters() {
        return this.additionalParameters;
    }

}
