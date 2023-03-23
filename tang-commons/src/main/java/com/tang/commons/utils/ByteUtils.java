package com.tang.commons.utils;

import java.text.DecimalFormat;

/**
 * 字节工具类
 *
 * @author Tang
 */
public class ByteUtils {

    private ByteUtils() {
    }

    /**
     * 格式化为带单位的字符串
     *
     * @param size 字节大小
     * @return 带单位的字符串
     */
    public static String getSize(long size) {
        if (size <= 0) {
            return "0 B";
        }
        final String[] units = { "B", "KB", "MB", "GB", "TB", "PB", "EB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
