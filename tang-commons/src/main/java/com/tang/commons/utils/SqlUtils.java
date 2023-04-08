package com.tang.commons.utils;

import org.apache.commons.lang3.StringUtils;

import com.tang.commons.exception.UtilsException;

/**
 * sql工具类
 *
 * @author Tang
 */
public class SqlUtils {

    private SqlUtils() {
    }

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static final String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotBlank(value) && !isValidOrderBySql(value)) {
            throw new UtilsException("查询失败, 参数不符合规范");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

}
