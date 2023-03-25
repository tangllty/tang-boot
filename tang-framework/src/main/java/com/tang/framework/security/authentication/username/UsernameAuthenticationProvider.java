package com.tang.framework.security.authentication.username;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.tang.commons.core.model.UserModel;
import com.tang.commons.enumeration.LoginType;
import com.tang.framework.web.service.AuthenticationService;
import com.tang.system.service.SysUserService;

/**
 * 用户名密码身份验证
 *
 * @author Tang
 */
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysUserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        var authenticationToken = (UsernameAuthenticationToken) authentication;
        var username = authenticationToken.getPrincipal().toString();
        var password = authenticationToken.getCredentials().toString();

        var user = userService.selectUserByUsername(username);

        UserModel userModel = authenticationService.createUserModel(user, password, LoginType.USERNAME.getName());

        authenticationToken = new UsernameAuthenticationToken(userModel, Collections.emptyList());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernameAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
