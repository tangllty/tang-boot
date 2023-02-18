package com.tang.commons.utils;

/**
 * 字符串工具类
 *
 * @author Tang
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * 默认占位符
     */
    private static final String DEFAULT_PLACE_HOLDER = "{}";

    /**
     * 格式化字符串
     *
     * @param format 模板
     * @param args 参数列表
     * @return 结果
     */
    public static String format(String format, Object... args) {
        return formatWith(format, DEFAULT_PLACE_HOLDER, args);
    }

    /**
     * 格式化字符串
     *
     * @param format 模板
     * @param placeHolder 占位符
     * @param args 参数列表
     * @return 结果
     */
    public static String formatWith(String format, String placeHolder, Object... args) {
        if (format == null || placeHolder == null || args == null) {
            return format == null ? "" : format;
        }

        var formatLength = format.length();
        var placeHolderLength = placeHolder.length();

        var stringBuilder = new StringBuilder(formatLength << 1);

        // 记录已经处理到的位置
        var placeHolderIndex = 0;
        // 占位符所在位置
        var placeHolderPosition = -1;

        for (Object arg: args) {
            placeHolderPosition = format.indexOf(placeHolder, placeHolderIndex);
            // 剩余部分无占位符
            if (placeHolderPosition == -1) {
                // 不带占位符的模板直接返回
                if (placeHolderIndex == 0) {
                    return format;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                stringBuilder.append(format, placeHolderIndex, formatLength);
                return stringBuilder.toString();
            }

            stringBuilder.append(format, placeHolderIndex, placeHolderPosition);
            stringBuilder.append(arg);
            placeHolderIndex = placeHolderPosition + placeHolderLength;
        }

        // 加入最后一个占位符后所有的字符
        stringBuilder.append(format, placeHolderIndex, formatLength);

        return stringBuilder.toString();
    }

}