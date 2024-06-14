package com.tang.commons.utils.queue.bootstrap

import com.tang.commons.utils.LogUtils
import com.tang.commons.utils.queue.factory.QueueDefaultThreadFactory
import com.tang.commons.utils.queue.wheel.SlotTask
import com.tang.commons.utils.queue.wheel.WheelQueue
import org.slf4j.Logger
import java.time.LocalDateTime
import java.util.TimerTask
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * 队列扫描定时器
 *
 * @author Tang
 */
class QueueScanTimer(

    /**
     * 环形队列
     */
    private val queue: WheelQueue

) : TimerTask() {

    companion object {
        private val LOGGER: Logger = LogUtils.getLogger()

        private val slotThreadFactory: ThreadFactory = QueueDefaultThreadFactory("slotThreadGroup")

        private val taskThreadFactory: ThreadFactory = QueueDefaultThreadFactory("taskThreadGroup")
    }

    /**
     * 处理每个槽位的线程，循环到这个槽位，立即丢到一个线程去处理，然后继续循环队列
     */
    private val slotPool = ThreadPoolExecutor(60, 60, 0L, TimeUnit.MILLISECONDS, LinkedBlockingQueue(), slotThreadFactory)

    /**
     * 处理每一个槽位中task集合的线程，集合中的每个任务一个线程
     */
    private val taskPool = ThreadPoolExecutor(1000, 1000, 0L, TimeUnit.MILLISECONDS, LinkedBlockingQueue(), taskThreadFactory)

    override fun run() {
        val now = LocalDateTime.now()
        val currentSecond = (now.minute * 60 + now.second) % (1 shl 10)
        val slot = queue.peek(currentSecond)
        LOGGER.debug("current slot: {}", currentSecond)
        slotPool.execute(SlotTask(slot.tasks, currentSecond, taskPool, queue))
    }

}
