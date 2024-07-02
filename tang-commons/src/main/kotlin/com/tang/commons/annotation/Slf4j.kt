package com.tang.commons.annotation

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Slf4j 注解
 *
 * @author Tang
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Slf4j {
    companion object {
        val <reified T> T.LOGGER: Logger
        inline get() = LoggerFactory.getLogger(T::class.java)
    }
}
