package com.tang.queue.wheel

import com.tang.commons.utils.LogUtils
import com.tang.queue.task.AbstractTask
import com.tang.queue.task.TaskAttribute
import org.slf4j.Logger
import java.util.concurrent.TimeUnit

/**
 * 时间轮队列。像轮子一样转动的队列， 环形队列、循环队列。
 *
 * @author Tang
 */
class WheelQueue(

    /**
     * 槽位数量
     */
    private val ticksPerWheel: Int,

    /**
     * 槽位时间间隔
     */
    private val tickDuration: Long,

    /**
     * 时间单位
     */
    private val tickUnit: TimeUnit,

    /**
     * 延时单位
     */
    private val delayUnit: TimeUnit

) {

    companion object {
        private val LOGGER: Logger = LogUtils.getLogger()
    }

    /**
     * 环形队列中的槽位
     */
    private val slotQueue: Array<Slot> = Array(findNextPositivePowerOfTwo(ticksPerWheel)) { Slot() }

    /**
     * 任务 ID 对应的槽位等任务属性
     */
    private val taskSlotMapping: MutableMap<String, TaskAttribute> = HashMap(slotQueue.size shl 1)

    /**
     * 计算下一个 2 的幂次方
     */
    private fun findNextPositivePowerOfTwo(value: Int): Int {
        assert(value > Int.MIN_VALUE && value < 0x40000000)
        return 1 shl (32 - Integer.numberOfLeadingZeros(value - 1))
    }

    /**
     * 添加一个任务到环形队列
     *
     * @param taskId 任务 ID
     * @param delay  延迟时间
     * @param task   任务
     */
    fun add(taskId: String, delay: Int, task: Runnable) {
        add(taskId, delay, delayUnit, task)
    }

    /**
     * 添加一个任务到环形队列
     *
     * @param taskId 任务 ID
     * @param delay  延迟时间
     * @param task   任务
     */
    fun add(taskId: String, delay: Long, task: Runnable) {
        add(taskId, delay, delayUnit, task)
    }

    /**
     * 添加一个任务到环形队列
     *
     * @param taskId 任务 ID
     * @param delay  延迟时间
     * @param unit   时间单位
     * @param task   任务
     */
    fun add(taskId: String, delay: Int, unit: TimeUnit, task: Runnable) {
        add(object : AbstractTask(taskId) {
            override fun run() {
                task.run()
            }
        }, delay, unit)
    }

    /**
     * 添加一个任务到环形队列
     *
     * @param taskId 任务 ID
     * @param delay  延迟时间
     * @param unit   时间单位
     * @param task   任务
     */
    fun add(taskId: String, delay: Long, unit: TimeUnit, task: Runnable) {
        add(object : AbstractTask(taskId) {
            override fun run() {
                task.run()
            }
        }, delay, unit)
    }

    /**
     * 添加一个任务到环形队列
     *
     * @param task  任务
     * @param delay 延迟时间
     * @param unit  时间单位
     */
    fun add(task: AbstractTask, delay: Int, unit: TimeUnit = delayUnit) {
        add(task, delay.toLong(), unit)
    }

    /**
     * 添加一个任务到环形队列
     *
     * @param task  任务
     * @param delay 延迟时间
     * @param unit  时间单位
     */
    fun add(task: AbstractTask, delay: Long, unit: TimeUnit = delayUnit) {
        //设置任务熟悉
        val slotIndex = setAttribute(task, delay, unit)
        //加到对应槽位的集合中
        slotQueue[slotIndex].addTask(task)
        LOGGER.debug("join task.task => {}, slotIndex => {}", task, slotIndex)
    }

    private fun setAttribute(task: AbstractTask, delay: Long, unit: TimeUnit): Int {
        val duration = unit.toNanos(delay)
        val now = System.nanoTime()
        val slotIndex = (now + duration) / tickUnit.toNanos(tickDuration) % ticksPerWheel
        val taskAttribute = TaskAttribute()
        taskAttribute.joinTime = now
        taskAttribute.slotIndex = slotIndex.toInt()
        taskAttribute.executeTime = now + duration
        taskSlotMapping[task.id] = taskAttribute
        return slotIndex.toInt()
    }

    /**
     * 获取指定索引槽位中的数据
     */
    fun peek(index: Int): Slot {
        return slotQueue[index]
    }

    /**
     * 从环形队列中移除一个任务
     *
     * @param taskId 任务 ID
     */
    fun remove(taskId: String) {
        taskSlotMapping[taskId]?.let {
            taskSlotMapping.remove(taskId)
            slotQueue[it.slotIndex].removeTask(taskId)
        }
    }

}
