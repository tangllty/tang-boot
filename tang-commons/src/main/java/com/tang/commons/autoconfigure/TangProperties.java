package com.tang.commons.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目配置
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(TangProperties.TANG_PREFIX)
public class TangProperties {

    public static final String TANG_PREFIX = "tang";

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目版本
     */
    private String version;

    /**
     * 文件上传路径
     */
    private String uploads;

    /**
     * 演示模式
     */
    private boolean demoMode;

    /**
     * 单用户登陆
     */
    private boolean singleLogin;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUploads() {
        return uploads;
    }

    public void setUploads(String uploads) {
        this.uploads = uploads;
    }

    public boolean isDemoMode() {
        return demoMode;
    }

    public void setDemoMode(boolean demoMode) {
        this.demoMode = demoMode;
    }

    public boolean isSingleLogin() {
        return singleLogin;
    }

    public void setSingleLogin(boolean singleLogin) {
        this.singleLogin = singleLogin;
    }

}
