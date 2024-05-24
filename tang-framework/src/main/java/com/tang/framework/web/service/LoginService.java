package com.tang.framework.web.service;

import java.util.Collections;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tang.commons.autoconfigure.TangProperties;
import com.tang.commons.constants.CachePrefix;
import com.tang.commons.enumeration.LoginType;
import com.tang.commons.exception.CaptchaException;
import com.tang.commons.exception.user.IllegalLoginTypeException;
import com.tang.commons.model.LoginModel;
import com.tang.commons.model.UserModel;
import com.tang.commons.utils.Assert;
import com.tang.commons.utils.LogUtils;
import com.tang.commons.utils.RedisUtils;
import com.tang.commons.utils.StringUtils;
import com.tang.framework.security.authentication.email.EmailAuthenticationToken;
import com.tang.framework.security.authentication.github.GitHubAuthenticationToken;
import com.tang.framework.security.authentication.username.UsernameAuthenticationToken;
import com.tang.framework.utils.AuthenticationUtils;
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

    private final TangProperties tangProperties;

    public LoginService(AuthenticationManager authenticationManager, TokenService tokenService, SysLogLoginService logLoginService,
            RedisUtils redisUtils, TangProperties tangProperties) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.logLoginService = logLoginService;
        this.redisUtils = redisUtils;
        this.tangProperties = tangProperties;
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
        verifyCaptcha(loginModel);

        Authentication authentication;
        AbstractAuthenticationToken authenticationToken;

        authenticationToken = switch (LoginType.getLoginType(loginModel.getLoginType())) {
            case USERNAME -> new UsernameAuthenticationToken(loginModel.getUsername(), loginModel.getPassword());
            case EMAIL -> new EmailAuthenticationToken(loginModel.getEmail(), loginModel.getPassword());
            case GITHUB -> new GitHubAuthenticationToken(loginModel.getCode());
            default -> throw new IllegalLoginTypeException("Unexpected login type: " + loginModel.getLoginType());
        };

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        var account = authenticationToken.getPrincipal().toString();
        authentication = authenticationManager.authenticate(authenticationToken);

        var userModel = (UserModel) authentication.getPrincipal();

        var message = "登陆成功";

        if (tangProperties.getSingleLogin()) {
            var userIdCacheKey = CachePrefix.LOGIN_USER_ID + userModel.getUser().getUserId();
            var userIdCacheValue = redisUtils.get(userIdCacheKey);
            if (Objects.nonNull(userIdCacheValue)) {
                var userCache = (UserModel) redisUtils.get(String.valueOf(userIdCacheValue));
                message = StringUtils.format("登录成功，您的账号已在{}使用{}登录，已将其下线", userCache.getLocation(), userCache.getBrowser());
                redisUtils.delete(String.valueOf(userIdCacheValue));
                redisUtils.delete(userIdCacheKey);
            }
        }

        // 认证成功后，重新设置认证信息
        authenticationToken = AuthenticationUtils.newInstance(loginModel.getLoginType(), userModel, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        var token = tokenService.createToken(userModel);

        logLoginService.recordLoginInfo(userModel.getUser().getUserId(), userModel, account, loginModel.getLoginType(), true, message);
        LOGGER.info("用户使用 {} 方式登陆成功，登陆账号：{}", loginModel.getLoginType(), account);

        return token;
    }

    /**
     * 验证码校验
     *
     * @param loginModel 登陆用户信息
     */
    public void verifyCaptcha(LoginModel loginModel) {
        if (!loginModel.getCaptchaEnable()) {
            return;
        }

        var captcha = redisUtils.get(CachePrefix.CAPTCHA + loginModel.getCaptcha().getId());

        Assert.isNull(captcha, new CaptchaException("验证码已过期"));
        Assert.isFalse(captcha.equals(loginModel.getCaptcha().getText()), new CaptchaException("验证码错误"));
    }

}
