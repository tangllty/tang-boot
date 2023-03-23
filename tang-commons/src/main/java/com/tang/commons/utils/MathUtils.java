package com.tang.commons.utils;

/**
 * 数学工具类
 *
 * @author Tang
 */
public class MathUtils {

    private MathUtils() {
    }

    /**
     * 获取百分比占用, 保留两位小数
     *
     * @param total 总大小
     * @param used  已使用大小
     * @return 百分比
     */
    public static String getPercent(long total, long used) {
        return String.format("%.2f", (used * 1.0 / total) * 100);
    }

}
