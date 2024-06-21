package com.tang.queue.wheel

import com.tang.queue.task.AbstractTask
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ThreadPoolExecutor

/**
 * 槽位任务
 *
 * @author Tang
 */
class SlotTask(

    /**
     * 任务集合
     */
    private var tasks: ConcurrentMap<String, AbstractTask>,

    /**
     * 任务线程池
     */
    private var taskPool: ThreadPoolExecutor,

    /**
     * 环形队列
     */
    private var queue: WheelQueue

) : Runnable {

    override fun run() {
        tasks.forEach { (taskId, task) ->
            if (task.cycleNum <= 0) {
                taskPool.execute(task)
                tasks.remove(taskId)
                queue.remove(taskId)
            } else {
                task.decrementCycle()
            }
        }
    }

}
