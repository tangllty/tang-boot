package com.tang.commons.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * 项目配置
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(TangProperties.TANG_PREFIX)
class TangProperties {

    companion object {
        const val TANG_PREFIX = "tang"
    }

    /**
     * 项目名称
     */
    var name: String? = null

    /**
     * 项目版本
     */
    var version: String? = null

    /**
     * 文件上传路径
     */
    var uploads: String? = null

    /**
     * 演示模式
     */
    var demoMode = false

    /**
     * 单用户登陆
     */
    var singleLogin = false

}
