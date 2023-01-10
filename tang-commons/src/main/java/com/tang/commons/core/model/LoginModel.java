package com.tang.commons.core.model;

import java.io.Serializable;

/**
 * 登陆模型
 *
 * @author Tang
 */
public class LoginModel implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = -6970037015048370182L;

    private String username;

    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
