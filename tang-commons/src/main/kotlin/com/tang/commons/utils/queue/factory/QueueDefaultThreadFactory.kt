package com.tang.commons.utils.queue.factory

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * 队列默认线程工厂
 *
 * @author Tang
 */
class QueueDefaultThreadFactory(groupName: String) : ThreadFactory {

    companion object {
        private val POOL_NUMBER = AtomicInteger(1)
    }

    private val group: ThreadGroup = Thread.currentThread().threadGroup

    private val threadNumber = AtomicInteger(1)

    private val namePrefix: String = groupName + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-"

    override fun newThread(runnable: Runnable): Thread {
        val thread = Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0)
        if (thread.isDaemon) {
            thread.isDaemon = false
        }
        if (thread.priority != Thread.NORM_PRIORITY) {
            thread.priority = Thread.NORM_PRIORITY
        }
        return thread
    }

}
