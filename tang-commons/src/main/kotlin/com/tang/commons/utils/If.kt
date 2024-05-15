package com.tang.commons.utils

import java.util.Objects

/**
 * @author Tang
 */
object If {

    /**
     * 判断是否为空
     */
    @JvmStatic
    fun isEmpty(any: Any?): Boolean {
        if (Objects.isNull(any)) {
            return true
        }
        return when (any) {
            is String -> any.isEmpty()
            is Array<*> -> any.isEmpty()
            is Map<*, *> -> any.isEmpty()
            is Collection<*> -> any.isEmpty()
            else -> false
        }
    }

    /**
     * 判断是否非空
     */
    @JvmStatic
    fun nonEmpty(any: Any?): Boolean {
        return !isEmpty(any)
    }

}
