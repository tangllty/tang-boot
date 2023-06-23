package com.tang.commons.core.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 登陆模型
 *
 * @author Tang
 */
public class LoginModel implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = -5428882047565357006L;

    /**
     * 用户名
     */
    @Length(min = 4, max = 32, message = "用户名长度应在 4 到 32 之间")
    private String username;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Length(min = 4, max = 64, message = "邮箱长度应在 4 到 64 之间")
    private String email;

    /**
     * 密码
     */
    @Length(min = 4, max = 32, message = "密码长度应在 4 到 32 之间")
    private String password;

    /**
     * 登陆方式
     */
    @NotBlank(message = "登陆方式不能为空")
    private String loginType;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
