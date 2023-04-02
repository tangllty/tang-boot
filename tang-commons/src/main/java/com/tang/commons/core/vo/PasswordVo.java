package com.tang.commons.core.vo;

/**
 * 密码对象
 *
 * @author Tang
 */
public class PasswordVo {

    /**
     * 用户主键
     */
    private Long userId;

    /**
    * 旧密码
    */
    private String oldPassword;

    /**
    * 新密码
    */
    private String newPassword;

    /**
     * 确认密码
     */
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
