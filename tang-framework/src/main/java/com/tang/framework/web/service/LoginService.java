package com.tang.framework.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tang.commons.constants.LoginType;
import com.tang.commons.core.model.LoginModel;
import com.tang.commons.core.model.UserModel;
import com.tang.framework.security.authentication.email.EmailAuthenticationToken;
import com.tang.framework.security.authentication.username.UsernameAuthenticationToken;

/**
 * 登陆服务
 *
 * @author Tang
 */
@Component
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

     @Autowired
     private AuthenticationManager authenticationManager;

     @Autowired
     private TokenService tokenService;

    /**
     * 登陆
     *
     * @param loginModel 登陆用户信息
     * @return token
     *
     * @see com.tang.framework.security.authentication.username.UsernameAuthenticationProvider#authenticate(Authentication)
     */
    public String login(LoginModel loginModel) {
        Authentication authentication = null;
        AbstractAuthenticationToken authenticationToken = null;

        switch (loginModel.getLoginType()) {
            case LoginType.USERNAME -> authenticationToken = new UsernameAuthenticationToken(loginModel.getUsername(), loginModel.getPassword());
            case LoginType.EMAIL -> authenticationToken = new EmailAuthenticationToken(loginModel.getEmail(), loginModel.getPassword());
        }

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);

        var userModel = (UserModel) authentication.getPrincipal();

        return tokenService.createToken(userModel);
    }

}
