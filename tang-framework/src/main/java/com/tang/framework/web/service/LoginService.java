package com.tang.framework.web.service;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tang.commons.core.model.LoginModel;
import com.tang.commons.core.model.UserModel;
import com.tang.commons.enumeration.LoginType;
import com.tang.commons.exception.user.IllegalLoginTypeException;
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

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final SysLogLoginService logLoginService;

    public LoginService(AuthenticationManager authenticationManager, TokenService tokenService, SysLogLoginService logLoginService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.logLoginService = logLoginService;
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

        var token = tokenService.createToken(userModel);

        logLoginService.recordLoginInfo(userModel.getUser().getUserId(), userModel, account, LoginType.USERNAME.getName(), true, "登陆成功");

        return token;
    }

}
