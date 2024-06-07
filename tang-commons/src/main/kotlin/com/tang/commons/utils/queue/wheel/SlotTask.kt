package com.tang.commons.utils.queue.wheel

import com.tang.commons.utils.LogUtils
import com.tang.commons.utils.queue.task.AbstractTask
import org.slf4j.Logger
import java.util.Queue
import java.util.concurrent.ThreadPoolExecutor

/**
 * 槽位任务
 *
 * @author Tang
 */
class SlotTask(
    private var tasks: Queue<AbstractTask>,
    private var currentSecond: Int,
    private var taskPool: ThreadPoolExecutor,
    private var queue: WheelQueue
) : Runnable {

    companion object {
        private val LOGGER: Logger = LogUtils.getLogger()
    }

    override fun run() {
        var taskId: String
        val it = tasks.iterator()
        while (it.hasNext()) {
            val task = it.next()
            LOGGER.debug(
                "running_current_slot:currentSecond => {}, task => {}, taskQueueSize => {}",
                currentSecond, task.toString(), tasks.size
            )
            taskId = task.id
            if (task.cycleNum <= 0) {
                taskPool.execute(task)
                it.remove()
                queue.remove(taskId)
            } else {
                LOGGER.debug(
                    "decrementCycle#running_current_solt:currentSecond => {}, task => {}",
                    currentSecond, task
                )
                task.decrementCycle()
            }
        }
    }

}
