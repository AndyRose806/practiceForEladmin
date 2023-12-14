package com.pubo.security.config;

import com.pubo.security.config.bean.SecurityProperties;
import com.pubo.security.security.TokenFilter;
import com.pubo.security.security.TokenProvider;
import com.pubo.security.service.OnlineUserService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final TokenProvider tokenProvider;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;

    public FilterConfig(TokenProvider tokenProvider, SecurityProperties properties, OnlineUserService onlineUserService) {
        this.tokenProvider = tokenProvider;
        this.properties = properties;
        this.onlineUserService = onlineUserService;
    }


    @Bean
    public FilterRegistrationBean<TokenFilter> tokenFilter() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenFilter(tokenProvider,properties,onlineUserService));
        registrationBean.addUrlPatterns("/api/*", "/app/*");
        return registrationBean;
    }
}
