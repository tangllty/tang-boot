package com.tang.commons.utils.queue.wheel

import com.tang.commons.utils.LogUtils
import com.tang.commons.utils.queue.config.QueueConfig
import com.tang.commons.utils.queue.task.AbstractTask
import com.tang.commons.utils.queue.task.TaskAttribute
import org.slf4j.Logger
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

/**
 * 时间轮队列。像轮子一样转动的队列， 环形队列、循环队列。
 *
 * @author Tang
 */
class WheelQueue {

    companion object {
        private val LOGGER: Logger = LogUtils.getLogger()
    }

    /**
     * 建立一个有3600个槽位的环形队列。每秒轮询一个槽位，3600个就是3600秒=1小时
     */
    private val slotQueue: Array<Slot?> = arrayOfNulls(QueueConfig.QUEUE_SIZE)

    /**
     * 任务 ID 对应的槽位等任务属性
     */
    private val taskSlotMapping: Map<String, TaskAttribute> = HashMap(1 shl 10) // aka 1024

    init {
        for (i in 0 until QueueConfig.QUEUE_SIZE) {
            slotQueue[i] = Slot()
        }
    }

    /**
     * 添加一个任务到环形队列
     *
     * @param task         任务
     * @param secondsLater 以当前时间点为基准，多少秒以后执行
     */
    fun add(task: AbstractTask, secondsLater: Int) {
        //设置任务熟悉
        val slotIndex = setAttribute(secondsLater, task, taskSlotMapping)
        //加到对应槽位的集合中
        slotQueue[slotIndex]!!.addTask(task)
        LOGGER.debug("join task.task => {}, slotIndex => {}", task, slotIndex)
    }

    private fun setAttribute(
        secondsLater: Int,
        task: AbstractTask,
        taskSlotMapping: Map<String, TaskAttribute>
    ): Int {
        val taskAttribute = TaskAttribute()
        val now = LocalDateTime.now()
        val currentSecond = now.minute * 60 + now.second
        val slotIndex = (currentSecond + secondsLater) % QueueConfig.QUEUE_SIZE

        task.cycleNum = secondsLater / QueueConfig.QUEUE_SIZE

        val future = now.plusSeconds(secondsLater.toLong())
        taskAttribute.executeTime = Date.from(future.atZone(ZoneId.systemDefault()).toInstant())
        taskAttribute.slotIndex = slotIndex
        taskAttribute.joinTime = Date.from(now.atZone(ZoneId.systemDefault()).toInstant())
        (taskSlotMapping as MutableMap)[task.id] = taskAttribute

        return slotIndex
    }

    /**
     * 获取指定索引槽位中的数据
     */
    fun peek(index: Int): Slot {
        return slotQueue[index]!!
    }

    /**
     * 从环形队列中移除一个任务
     *
     * @param taskId 任务 ID
     */
    fun remove(taskId: String) {
        val taskAttribute = taskSlotMapping[taskId]
        if (taskAttribute != null) {
            slotQueue[taskAttribute.slotIndex]!!.removeTask(taskId)
        }
    }

}