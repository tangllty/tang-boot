package com.tang.framework.web.service;

import org.springframework.stereotype.Component;

/**
 * 登陆服务
 *
 * @author Tang
 */
@Component
public class LoginService {

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     *
     * @see com.tang.framework.web.service.UserDetailsServiceImpl#loadUserByUsername(String)
     */
    public String login(String username, String password) {
        // TODO
        return null;
    }

}
