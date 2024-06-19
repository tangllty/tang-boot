package com.tang.queue.task

/**
 * 任务抽象类
 *
 * @author Tang
 */
abstract class AbstractTask(

    /**
     * 任务 ID
     */
    var id: String

) : Runnable {

    /**
     * 圈数
     */
    var cycleNum: Int = 0

    /**
     * 圈数减一
     */
    fun decrementCycle() {
        cycleNum--
    }

}
