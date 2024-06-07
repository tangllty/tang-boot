package com.tang.commons.utils.queue.wheel

import com.tang.commons.utils.queue.task.AbstractTask
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * 槽位
 *
 * @author Tang
 */
class Slot {

    val tasks: Queue<AbstractTask> = ConcurrentLinkedQueue()

    fun addTask(task: AbstractTask) {
        tasks.add(task)
    }

    fun removeTask(taskId: String) {
        tasks.removeIf { it.id == taskId }
    }

}
