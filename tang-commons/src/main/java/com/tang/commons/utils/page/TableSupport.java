package com.tang.commons.utils.page;

import org.apache.commons.lang3.StringUtils;

import com.tang.commons.utils.ServletUtils;

/**
 * 表格数据处理
 *
 * @author Tang
 */
public class TableSupport {

    private TableSupport() {
    }

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 分页参数合理化
     */
    public static final String REASONABLE = "reasonable";

    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain() {
        var pageDomain = new PageDomain();
        pageDomain.setPageNum(toInteger(ServletUtils.getParameter(PAGE_NUM), 1));
        pageDomain.setPageSize(toInteger(ServletUtils.getParameter(PAGE_SIZE), 10));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        pageDomain.setReasonable(toBoolean(ServletUtils.getParameter(REASONABLE)));
        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }

    /**
     * 转换为 Integer
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    private static Integer toInteger(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Integer integer) {
            return integer;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        var valueStr = String.valueOf(value);
        if (StringUtils.isBlank(valueStr)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(valueStr.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 转换为boolean
     *
     * @param value 被转换的值
     * @return 结果
     */
    private static Boolean toBoolean(Object value) {
        return toBoolean(value, null);
    }

    /**
     * 转换为boolean
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    private static Boolean toBoolean(Object value, Boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }
        var valueStr = String.valueOf(value);
        if (StringUtils.isBlank(valueStr)) {
            return defaultValue;
        }
        valueStr = valueStr.trim().toLowerCase();
        return switch (valueStr) {
            case "true", "1" -> true;
            case "false", "0" -> false;
            default -> defaultValue;
        };
    }

}
