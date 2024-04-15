package com.tang.commons.autoconfigure.tang

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * 日期格式配置
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(DateFormatProperties.DATE_FORMAT_PREFIX)
class DateFormatProperties {

    companion object {
        const val DATE_FORMAT_PREFIX = "tang.date-format"
    }

    /**
     * 日期格式
     */
    var localDate: String = "yyyy-MM-dd"

    /**
     * 日期时间格式
     */
    var localDateTime: String = "yyyy-MM-dd HH:mm:ss"

}
