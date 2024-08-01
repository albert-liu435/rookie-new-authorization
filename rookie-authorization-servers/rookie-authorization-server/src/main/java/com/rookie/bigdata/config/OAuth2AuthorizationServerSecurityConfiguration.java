package com.rookie.bigdata.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Class OAuth2AuthorizationServerSecurityConfiguration
 * @Description 认证配置
 * <p>
 * {@link EnableMethodSecurity} 开启全局方法认证，启用JSR250注解支持，启用注解 {@link Secured} 支持，
 * 在Spring Security 6.0版本中将@Configuration注解从@EnableWebSecurity, @EnableMethodSecurity, @EnableGlobalMethodSecurity
 * 和 @EnableGlobalAuthentication 中移除，使用这些注解需手动添加 @Configuration 注解
 * {@link EnableWebSecurity} 注解有两个作用:
 * 1. 加载了WebSecurityConfiguration配置类, 配置安全认证策略。
 * 2. 加载了AuthenticationConfiguration, 配置了认证信息。
 * @Author rookie
 * @Date 2024/8/1 14:15
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
//@ConditionalOnBean({RegisteredClientRepository.class, AuthorizationServerSettings.class})
public class OAuth2AuthorizationServerSecurityConfiguration {

//    @Configuration(proxyBeanMethods = false)
//    @ConditionalOnDefaultWebSecurity
//    @ConditionalOnBean({ RegisteredClientRepository.class, AuthorizationServerSettings.class })
//    class OAuth2AuthorizationServerWebSecurityConfiguration {

    /**
     * 配置端点的过滤器
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {


        // 配置默认的设置，忽略认证端点的csrf校验
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

//        http
//                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
//                // 开启OpenID Connect 1.0协议相关端点
//                .oidc(withDefaults());
        http
                // 处理使用access token访问用户信息端点和客户端注册端点
                .oauth2ResourceServer(
                        (resourceServer) -> resourceServer
                                .jwt(withDefaults())
                );
        http
                // 当未登录时访问认证端点时重定向至login页面
                .exceptionHandling(
                        (exceptions) -> exceptions
                                .defaultAuthenticationEntryPointFor(
                                        new LoginUrlAuthenticationEntryPoint("/login"), createRequestMatcher())
                );
        return http.build();
    }

    /**
     * 配置认证相关的过滤器
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(withDefaults());
//        // 指定登录页面,
//                .formLogin(formLogin ->
//                formLogin.loginPage("/login")
//        );

        // 指定登录页面,因为默认为/login，所以这里应该也可以写成
//                .formLogin(Customizer.withDefaults());

        // 添加BearerTokenAuthenticationFilter，将认证服务当做一个资源服务，解析请求头中的token
        http.oauth2ResourceServer((resourceServer) -> resourceServer
                .jwt(Customizer.withDefaults()));
        return http.build();
    }

    private static RequestMatcher createRequestMatcher() {
        MediaTypeRequestMatcher requestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
        requestMatcher.setIgnoredMediaTypes(Set.of(MediaType.ALL));
        return requestMatcher;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("admin", "normal", "unAuthentication")
                .authorities("app", "web", "/test2", "/test3")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

}
