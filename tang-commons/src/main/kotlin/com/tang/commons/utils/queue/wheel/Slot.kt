package com.tang.commons.utils.queue.wheel

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

import com.tang.commons.utils.queue.task.AbstractTask

/**
 * 槽位
 *
 * @author Tang
 */
class Slot {

    val tasks: ConcurrentMap<String, AbstractTask> = ConcurrentHashMap()

    fun addTask(task: AbstractTask) {
        tasks[task.id] = task
    }

    fun removeTask(taskId: String) {
        tasks.remove(taskId)
    }

}
