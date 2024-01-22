package com.tang.commons.utils.time

/**
 * 时间工具类
 *
 * @author Tang
 */
object TimeUtils {

    /**
     * 格式化时间，将时间戳转换为带单位的时间
     */
    @JvmStatic
    fun formatTime(timestamp: Long): String {
        val unitValueMap = mapOf(
            "y" to 52 * 7 * 24 * 60 * 60 * 1000L,
            "w" to 7 * 24 * 60 * 60 * 1000L,
            "d" to 24 * 60 * 60 * 1000L,
            "h" to 60 * 60 * 1000L,
            "m" to 60 * 1000L,
            "s" to 1000L,
            "ms" to 1L
        )
        return buildString {
            var remainingTime = timestamp
            unitValueMap.forEach { (timeUnit, unitValue) ->
                if (remainingTime >= unitValue) {
                    val unitCount = remainingTime / unitValue
                    append(unitCount).append(timeUnit)
                    remainingTime %= unitValue
                }
            }
        }
    }

}
