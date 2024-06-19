package com.tang.queue.autoconfigure

import com.tang.queue.bootstrap.QueueBootstrap
import com.tang.queue.wheel.WheelQueue
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 环形队列配置
 *
 * @author Tang
 */
@Configuration
@EnableConfigurationProperties(QueueProperties::class)
@ConditionalOnClass(WheelQueue::class)
@ConditionalOnProperty(prefix = QueueProperties.QUEUE_PREFIX, name = ["enabled"], havingValue = "true", matchIfMissing = true)
class WheelDelayQueueConfiguration {

    @Bean
    fun WheelQueue(queueProperties: QueueProperties): WheelQueue {
        return QueueBootstrap(
            queueProperties.ticksPerWheel,
            queueProperties.tickDuration,
            queueProperties.tickUnit,
            queueProperties.delayUnit
        ).start()
    }

}
