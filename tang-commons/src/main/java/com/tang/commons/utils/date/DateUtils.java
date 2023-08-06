package com.tang.commons.utils.date;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author Tang
 */
public class DateUtils {

    private DateUtils() {
    }

    /**
     * 获取聊天时间
     *
     * @param localDateTime 聊天时间
     * @return 聊天时间
     */
    public static String getChatTime(LocalDateTime localDateTime) {
        return getChatTime(localDateTime, true, false, true);
    }

    /**
     * 获取聊天时间
     *
     * @param localDateTime  聊天时间
     * @param is12HourFormat 是否12小时制
     * @param hasLeadingZero 是否补零
     * @param showPeriod     是否显示上午下午
     * @return 聊天时间
     */
    public static String getChatTime(LocalDateTime localDateTime, boolean is12HourFormat, boolean hasLeadingZero, boolean showPeriod) {
        var now = LocalDateTime.now();
        var monday = now.with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
        var sunday = monday.plusDays(6);

        if (localDateTime.toLocalDate().isAfter(now.toLocalDate())) {
            throw new IllegalArgumentException("Chat time cannot be later than now");
        }

        var pattern = new StringBuilder();

        // 今天
        if (localDateTime.toLocalDate().isEqual(now.toLocalDate())) {
            formatTime(pattern, is12HourFormat, hasLeadingZero, showPeriod);
        // 昨天
        } else if (localDateTime.toLocalDate().isEqual(now.toLocalDate().minusDays(1))) {
            pattern.append("昨天");
            notShowPeriod(pattern, showPeriod);
            formatTime(pattern, is12HourFormat, hasLeadingZero, showPeriod);
        // 本周
        } else if (localDateTime.isAfter(monday) && localDateTime.isBefore(sunday)) {
            pattern.append(localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
            notShowPeriod(pattern, showPeriod);
            formatTime(pattern, is12HourFormat, hasLeadingZero, showPeriod);
        // 本年
        } else if (localDateTime.getYear() == now.getYear()) {
            hasLeadingZero(pattern, hasLeadingZero, "MM月dd日", "M月d日");
            notShowPeriod(pattern, showPeriod);
            formatTime(pattern, is12HourFormat, hasLeadingZero, showPeriod);
        // 早于本年
        } else if (localDateTime.getYear() < now.getYear()) {
            hasLeadingZero(pattern, hasLeadingZero, "yyyy年MM月dd日", "yyyy年M月d日");
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern.toString(), Locale.CHINA));
    }

    /**
     * 格式化时间
     *
     * @param is12HourFormat 是否12小时制
     * @param hasLeadingZero 是否补零
     * @param showPeriod     是否显示上午下午
     * @param pattern        格式化模式
     */
    private static void formatTime(StringBuilder pattern, boolean is12HourFormat, boolean hasLeadingZero, boolean showPeriod) {
        if (showPeriod) {
            pattern.append("a ");
        }
        if (is12HourFormat) {
            hasLeadingZero(pattern, hasLeadingZero, "hh:mm:ss", "h:m:s");
        } else {
            hasLeadingZero(pattern, hasLeadingZero, "HH:mm:ss", "H:m:s");
        }
    }

    /**
     * 是否补零
     *
     * @param pattern        格式化模式
     * @param hasLeadingZero 是否补零
     * @param has            补零
     * @param notHas         不补零
     */
    private static void hasLeadingZero(StringBuilder pattern, boolean hasLeadingZero, String has, String notHas) {
        if (hasLeadingZero) {
            pattern.append(has);
        } else {
            pattern.append(notHas);
        }
    }

    /**
     * 不展示上午下午
     *
     * @param pattern    格式化模式
     * @param showPeriod 是否展示上午下午
     */
    private static void notShowPeriod(StringBuilder pattern, boolean showPeriod) {
        if (!showPeriod) {
            pattern.append(" ");
        }
    }

}
