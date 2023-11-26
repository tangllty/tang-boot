package com.tang.commons.autoconfigure.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * GitHub 配置
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(prefix = GitHubProperties.GITHUB_PREFIX)
public class GitHubProperties {

    public static final String GITHUB_PREFIX = "oauth.github";

    /**
     * 客户端 ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
