package com.tang.queue.bootstrap

import com.tang.commons.utils.LogUtils
import com.tang.queue.factory.QueueDefaultThreadFactory
import com.tang.queue.wheel.WheelQueue
import org.slf4j.Logger
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * 启动队列
 *
 * @author Tang
 */
class QueueBootstrap(

    /**
     * 槽位数量
     */
    private val ticksPerWheel: Int = (1 shl 9), // aka 512

    /**
     * 槽位时间间隔
     */
    private val tickDuration: Long = 100,

    /**
     * 时间单位
     */
    private val tickUnit: TimeUnit = TimeUnit.MILLISECONDS,

    /**
     * 延时单位
     */
    private val delayUnit: TimeUnit = TimeUnit.MILLISECONDS

) {

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
        LOGGER.info("Starting wheel delay queue scanner.")
        val wheelQueue = WheelQueue(ticksPerWheel, tickDuration, tickUnit, delayUnit)
        val timerTask = QueueScanTimer(wheelQueue, ticksPerWheel, tickDuration, tickUnit)
        newScheduledThreadPool.scheduleWithFixedDelay(timerTask, 0, tickDuration, tickUnit)
        LOGGER.info("Successfully started wheel delay queue scanner.")
        return wheelQueue
    }

    /**
     * 停止此队列运行，已运行的任务暂不停止
     */
    fun shutdown() {
        newScheduledThreadPool.shutdown()
    }

}
