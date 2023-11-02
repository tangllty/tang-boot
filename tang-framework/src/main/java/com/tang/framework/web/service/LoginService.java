package com.tang.framework.web.service;

import java.util.Collections;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tang.commons.constants.CachePrefix;
import com.tang.commons.enumeration.LoginType;
import com.tang.commons.exception.CaptchaException;
import com.tang.commons.exception.user.IllegalLoginTypeException;
import com.tang.commons.model.LoginModel;
import com.tang.commons.model.UserModel;
import com.tang.commons.utils.LogUtils;
import com.tang.commons.utils.RedisUtils;
import com.tang.framework.security.authentication.email.EmailAuthenticationToken;
import com.tang.framework.security.authentication.username.UsernameAuthenticationToken;
import com.tang.system.service.log.SysLogLoginService;

/**
 * 登陆服务
 *
 * @author Tang
 */
@Component
public class LoginService {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final SysLogLoginService logLoginService;

    private final RedisUtils redisUtils;

    public LoginService(AuthenticationManager authenticationManager, TokenService tokenService,
                        SysLogLoginService logLoginService, RedisUtils redisUtils) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.logLoginService = logLoginService;
        this.redisUtils = redisUtils;
    }

    /**
     * 登陆
     *
     * @param loginModel 登陆用户信息
     * @return token
     *
     * @see com.tang.framework.security.authentication.username.UsernameAuthenticationProvider#authenticate(Authentication)
     * @see com.tang.framework.security.authentication.email.EmailAuthenticationProvider#authenticate(Authentication)
     */
    public String login(LoginModel loginModel) {
        var captcha = redisUtils.get(CachePrefix.CAPTCHA + loginModel.getCaptcha().getId());

        if (Objects.isNull(captcha)) {
            throw new CaptchaException("验证码已过期");
        }

        if (!captcha.equals(loginModel.getCaptcha().getText())) {
            throw new CaptchaException("验证码错误");
        }

        Authentication authentication;
        AbstractAuthenticationToken authenticationToken;

        authenticationToken = switch (LoginType.getLoginType(loginModel.getLoginType())) {
            case USERNAME -> new UsernameAuthenticationToken(loginModel.getUsername(), loginModel.getPassword());
            case EMAIL -> new EmailAuthenticationToken(loginModel.getEmail(), loginModel.getPassword());
            default -> throw new IllegalLoginTypeException("Unexpected login type: " + loginModel.getLoginType());
        };

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        var account = authenticationToken.getPrincipal().toString();
        authentication = authenticationManager.authenticate(authenticationToken);

        var userModel = (UserModel) authentication.getPrincipal();

        // 认证成功后，重新设置认证信息
        authenticationToken = switch (LoginType.getLoginType(userModel.getLoginType())) {
            case USERNAME -> new UsernameAuthenticationToken(userModel, Collections.emptyList());
            case EMAIL -> new EmailAuthenticationToken(userModel, Collections.emptyList());
            default -> throw new IllegalLoginTypeException("Unexpected login type: " + userModel.getLoginType());
        };
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        var token = tokenService.createToken(userModel);

        logLoginService.recordLoginInfo(userModel.getUser().getUserId(), userModel, account, loginModel.getLoginType(), true, "登陆成功");
        LOGGER.info("用户使用 {} 方式登陆成功，登陆账号：{}", loginModel.getLoginType(), account);

        return token;
    }

}
