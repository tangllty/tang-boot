package com.tang.queue.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.concurrent.TimeUnit

/**
 * 队列配置
 *
 * @author Tang
 */
@ConfigurationProperties(prefix = QueueProperties.QUEUE_PREFIX)
class QueueProperties {

    companion object {
        const val QUEUE_PREFIX = "tang.queue"
    }

    /**
     * 是否启用
     */
    var enabled: Boolean = true

    /**
     * 槽位数量
     */
    var ticksPerWheel: Int = 1 shl 9

    /**
     * 槽位时间间隔
     */
    var tickDuration: Long = 100

    /**
     * 时间单位
     */
    var tickUnit: TimeUnit = TimeUnit.MILLISECONDS

    /**
     * 默认延时单位
     */
    var delayUnit: TimeUnit = TimeUnit.MILLISECONDS

}
