package com.tang.commons.autoconfigure.oauth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * GitHub 配置
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(prefix = GitHubProperties.GITHUB_PREFIX)
class GitHubProperties {

    companion object {
        const val GITHUB_PREFIX = "oauth.github"
    }

    /**
     * 客户端 ID
     */
    var clientId: String? = null

    /**
     * 客户端密钥
     */
    var clientSecret: String? = null

    /**
     * 回调地址
     */
    var redirectUrl: String? = null

}
