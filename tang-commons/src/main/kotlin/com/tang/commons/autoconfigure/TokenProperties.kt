package com.tang.commons.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * token 配置属性
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(TokenProperties.TOKEN_PREFIX)
class TokenProperties {

    companion object {
        const val TOKEN_PREFIX = "token"
    }

    /**
     * 令牌自定义标识
     */
    var header: String? = null

    /**
     * 令牌秘钥
     */
    var secret: String? = null

    /**
     * 时间颗粒度
     */
    var timeUnit: TimeUnit? = null

    /**
     * 令牌有效期
     */
    var expireTime: Long = 0

    fun getExpireTimeLong(): Long {
        return TimeUnit.MILLISECONDS.convert(this.expireTime, this.timeUnit);
    }

}
