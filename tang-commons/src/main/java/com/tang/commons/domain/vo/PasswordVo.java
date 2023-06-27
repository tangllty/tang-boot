package com.tang.commons.domain.vo;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * 密码对象
 *
 * @author Tang
 */
public class PasswordVo {

    /**
     * 用户主键
     */
    @NotBlank(message = "用户主键不能为空")
    private Long userId;

    /**
    * 旧密码
    */
    @Length(min = 4, max = 32, message = "旧密码长度应在 4 到 32 之间")
    private String oldPassword;

    /**
    * 新密码
    */
    @Length(min = 4, max = 32, message = "新密码长度应在 4 到 32 之间")
    private String newPassword;

    /**
     * 确认密码
     */
    @Length(min = 4, max = 32, message = "确认密码长度应在 4 到 32 之间")
    private String confirmPassword;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
