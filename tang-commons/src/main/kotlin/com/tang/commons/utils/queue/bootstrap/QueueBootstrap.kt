package com.tang.commons.utils.queue.bootstrap

import com.tang.commons.utils.LogUtils
import com.tang.commons.utils.queue.factory.QueueDefaultThreadFactory
import com.tang.commons.utils.queue.wheel.WheelQueue
import org.slf4j.Logger
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * 启动队列
 *
 * @author Tang
 */
class QueueBootstrap {

    companion object {
        private val LOGGER: Logger = LogUtils.getLogger()
    }

    private val newScheduledThreadPool: ScheduledExecutorService

    init {
        val threadFactory = QueueDefaultThreadFactory("queueBootstrapGroup")
        newScheduledThreadPool = Executors.newScheduledThreadPool(1, threadFactory)
    }

    /**
     * 创建一个环形队列；并开启定时扫描队列
     */
    fun start(): WheelQueue {
        LOGGER.info("Timer wheel queue scanner starting...")
        val wheelQueue = WheelQueue()
        val timerTask = QueueScanTimer(wheelQueue)
        newScheduledThreadPool.scheduleWithFixedDelay(timerTask, 0, 100, TimeUnit.MILLISECONDS)
        LOGGER.info("Timer wheel queue scanner start up.")
        return wheelQueue
    }

    /**
     * 停止此队列运行，已运行的任务暂不停止
     */
    fun shutdown() {
        newScheduledThreadPool.shutdown()
    }

}
