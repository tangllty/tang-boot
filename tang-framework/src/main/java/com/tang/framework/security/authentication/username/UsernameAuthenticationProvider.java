package com.tang.framework.security.authentication.username;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tang.commons.constants.LoginType;
import com.tang.commons.core.model.SysDeptModel;
import com.tang.commons.core.model.SysUserModel;
import com.tang.commons.utils.SecurityUtils;
import com.tang.system.service.SysUserService;

/**
 * @author Tang
 */
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsernameAuthenticationProvider.class);

    @Autowired
    private SysUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        var authenticationToken = (UsernameAuthenticationToken) authentication;
        LOGGER.info("UsernameAuthenticationToken authentication request: {}", authenticationToken);
        var username = authenticationToken.getPrincipal().toString();
        var password = authenticationToken.getCredentials().toString();

        var user = userService.selectUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // TODO 验证密码

        var sysUserModel = new SysUserModel();
        var sysDeptModel = new SysDeptModel();
        BeanUtils.copyProperties(user, sysUserModel);
        BeanUtils.copyProperties(user.getDept(), sysDeptModel);
        sysUserModel.setDept(sysDeptModel);
        var userModel = SecurityUtils.createUserModel(sysUserModel);
        userModel.setLoginType(LoginType.USERNAME);

        LOGGER.info("token details -> {}", authenticationToken.getDetails());

        authenticationToken = new UsernameAuthenticationToken(userModel, Collections.emptyList());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernameAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
