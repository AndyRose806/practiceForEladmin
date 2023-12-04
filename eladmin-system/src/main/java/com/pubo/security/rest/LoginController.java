package com.pubo.security.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pubo.exception.BadRequestException;
import com.pubo.security.config.bean.LoginCodeEnum;
import com.pubo.security.config.bean.LoginProperties;
import com.pubo.security.config.bean.SecurityProperties;
import com.pubo.security.security.TokenProvider;
import com.pubo.security.service.OnlineUserService;
import com.pubo.security.service.dto.AuthUsrDto;
import com.pubo.security.utils.CryptoUtil;
import com.pubo.utils.RedisUtils;
import com.wf.captcha.base.Captcha;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author:pubo
 * @Date:2023/11/2921:22
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    private final SecurityProperties properties;

    private final RedisUtils redisUtils;

    private final TokenProvider tokenProvider;

    private final OnlineUserService onlineUserService;

    @Resource
    private LoginProperties loginProperties;

    public LoginController(SecurityProperties properties, RedisUtils redisUtils, TokenProvider tokenProvider, OnlineUserService onlineUserService) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        this.tokenProvider = tokenProvider;
        this.onlineUserService = onlineUserService;
    }

    @PostMapping("/login")
    public void login(@Validated @RequestBody AuthUsrDto authUsrDto, HttpServletRequest request){
        String enPassword = authUsrDto.getPassword();
        // 查询验证码
        String code = (String) redisUtils.get(authUsrDto.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUsrDto.getCode()) || !authUsrDto.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        String dePassword = CryptoUtil.desEncrypt(enPassword, CryptoUtil.KEY, CryptoUtil.IV);

        String token = tokenProvider.createToken(authUsrDto);

        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
        }};

        if (loginProperties.isSingleLogin()) {
            // 踢掉之前已经登录的token
            onlineUserService.kickOutForUsername(authUsrDto.getUsername());
        }

        // 保存在线信息
        onlineUserService.save(authUsrDto, token, request);

        System.out.println(""+dePassword);
    }

    @GetMapping ("/code")
    public ResponseEntity<Object> getCode(){
        // 获取运算的结果
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }

//        // 保存
        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息

        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);

    }


}
