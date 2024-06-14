package com.tang.commons.utils.queue.task

import java.time.LocalDateTime

/**
 * 任务属性
 *
 * @author Tang
 */
class TaskAttribute {

    /**
     * 第几个槽位
     */
    var slotIndex = 0

    /**
     * 任务加入槽位的时间
     */
    lateinit var joinTime: LocalDateTime

    /**
     * 任务应该什么时候执行
     */
    lateinit var executeTime: LocalDateTime

}
