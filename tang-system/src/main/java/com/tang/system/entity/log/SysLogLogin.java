package com.tang.system.entity.log;

import java.util.Date;

import com.tang.commons.core.base.entity.BaseEntity;

/**
 * 登陆日志实体类 sys_log_login
 *
 * @author Tang
 */
public class SysLogLogin extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = 5985309601668830597L;

    /**
     * 日志ID
     */
    private Long loginId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登陆账号
     */
    private String account;

    /**
     * 登陆类型
     */
    private String loginType;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 是否成功
     */
    private String success;

    /**
     * 返回消息
     */
    private String message;


    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
