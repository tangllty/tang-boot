package com.tang.commons.domain.vo;

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
    @NotBlank(message = "密码不能为空")
    private String oldPassword;

    /**
    * 新密码
    */
    @NotBlank(message = "密码不能为空")
    private String newPassword;

    /**
     * 确认密码
     */
    @NotBlank(message = "密码不能为空")
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
