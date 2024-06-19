package com.tang.queue.task

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
     * 任务加入槽位的时间(nano)
     */
    var joinTime: Long = 0

    /**
     * 任务应该什么时候执行(nano)
     */
    var executeTime: Long = 0

}
