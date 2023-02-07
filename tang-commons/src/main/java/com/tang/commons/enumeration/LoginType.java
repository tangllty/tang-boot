package com.tang.commons.enumeration;

/**
 * 登陆方式
 *
 * @author Tang
 */
public enum LoginType {

    /**
     * 用户名密码
     */
    USERNAME("username"),

    /**
     * 邮箱密码
     */
    EMAIL("email");

    private String loginType;

    private LoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

}
