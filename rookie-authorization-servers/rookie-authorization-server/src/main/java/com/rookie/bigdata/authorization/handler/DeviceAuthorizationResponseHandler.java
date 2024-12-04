package com.rookie.bigdata.authorization.handler;

import com.rookie.bigdata.constant.SecurityConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2DeviceAuthorizationResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2DeviceAuthorizationResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2DeviceAuthorizationRequestAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.web.OAuth2DeviceAuthorizationEndpointFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * @Class DeviceAuthorizationResponseHandler
 * @Description 设备码认证成功响应
 * 参考{@link OAuth2DeviceAuthorizationEndpointFilter#sendDeviceAuthorizationResponse}实现
 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler
 * @see org.springframework.security.oauth2.server.authorization.web.OAuth2DeviceAuthorizationEndpointFilter
 * @Author rookie
 * @Date 2024/12/4 16:45
 * @Version 1.0
 */
public class DeviceAuthorizationResponseHandler implements AuthenticationSuccessHandler {

    private final HttpMessageConverter<OAuth2DeviceAuthorizationResponse> deviceAuthorizationHttpResponseConverter =
            new OAuth2DeviceAuthorizationResponseHttpMessageConverter();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2DeviceAuthorizationRequestAuthenticationToken deviceAuthorizationRequestAuthentication =
                (OAuth2DeviceAuthorizationRequestAuthenticationToken) authentication;

        OAuth2DeviceCode deviceCode = deviceAuthorizationRequestAuthentication.getDeviceCode();
        OAuth2UserCode userCode = deviceAuthorizationRequestAuthentication.getUserCode();

        // Generate the fully-qualified verification URI
        String issuerUri = AuthorizationServerContextHolder.getContext().getIssuer();
        UriComponentsBuilder uriComponentsBuilder;
        if (UrlUtils.isAbsoluteUrl(SecurityConstants.DEVICE_ACTIVATE_URI)) {
            uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(SecurityConstants.DEVICE_ACTIVATE_URI);
        } else {
            uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(issuerUri)
                    .path(SecurityConstants.DEVICE_ACTIVATE_URI);
        }
        String verificationUri = uriComponentsBuilder.build().toUriString();

        // 拼接user_code
        String verificationUriComplete = uriComponentsBuilder
                .queryParam(OAuth2ParameterNames.USER_CODE, userCode.getTokenValue())
                .build().toUriString();

        OAuth2DeviceAuthorizationResponse deviceAuthorizationResponse =
                OAuth2DeviceAuthorizationResponse.with(deviceCode, userCode)
                        .verificationUri(verificationUri)
                        .verificationUriComplete(verificationUriComplete)
                        .build();

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.deviceAuthorizationHttpResponseConverter.write(deviceAuthorizationResponse, null, httpResponse);
    }

}
