package com.pubo.security.security;

import cn.hutool.core.util.StrUtil;
import com.pubo.security.config.bean.SecurityProperties;
import com.pubo.security.entity.UsrRoleInfo;
import com.pubo.security.service.OnlineUserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "TokenFilter",urlPatterns = {"/api/*", "/app/*"})
public class TokenFilter extends GenericFilter {
    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);
    private final TokenProvider tokenProvider;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;




    /**
     * @param tokenProvider     Token
     * @param properties        JWT
     * @param onlineUserService 用户在线
     */
    public TokenFilter(TokenProvider tokenProvider, SecurityProperties properties, OnlineUserService onlineUserService) {
        this.properties = properties;
        this.onlineUserService = onlineUserService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = resolveToken(request);
        String requestURI = request.getRequestURI();

        // 对于 Token 为空的不需要去查 Redis
        if (StrUtil.isNotBlank(token)  ) {
            System.out.println("request"+requestURI);
            UsrRoleInfo usrRoleInfo = new UsrRoleInfo();
            boolean cleanUserCache = false;
            try {
                String loginKey = tokenProvider.loginKey(token);
                usrRoleInfo = onlineUserService.getOne(loginKey);
            }catch (ExpiredJwtException e) {
                log.error(e.getMessage());
                cleanUserCache = true;
            }
            if (usrRoleInfo != null && StringUtils.hasText(token)) {
                // Token 续期
                tokenProvider.checkRenewal(token);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(properties.getTokenStartWith(), "");
        } else {
            log.debug("非法Token：{}", bearerToken);
        }
        return null;
    }
}
