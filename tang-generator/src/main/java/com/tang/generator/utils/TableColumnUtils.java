package com.tang.generator.utils;

import java.util.List;

import com.google.common.base.CaseFormat;
import com.tang.generator.entity.GenTableColumn;

/**
 * 表字段工具类
 *
 * @author Tang
 */
public class TableColumnUtils {

    private TableColumnUtils() {
    }

    /**
     * 数据库字符串类型
     */
    private static final String[] STRING_TYPE = {"char", "varchar", "text", "mediumtext", "longtext"};

    /**
     * 数据库数字类型
     */
    private static final String[] NUMBER_TYPE = {"tinyint", "smallint", "int", "bigint", "float", "double", "decimal"};

    /**
     * 数据库时间类型
     */
    private static final String[] DATE_TYPE = {"date", "time", "datetime", "timestamp"};

    /**
     * 父类字段
     */
    private static final String[] SUPER_FIELD = {"createBy", "createTime", "updateBy", "updateTime", "remark"};

    /**
     * 不会被插入的字段
     */
    private static final String[] NOT_INSERT_FIELD = {"updateBy", "updateTime"};

    /**
     * 不会被编辑的字段
     */
    private static final String[] NOT_EDIT_FIELD = {"createBy", "createTime"};

    /**
     * 初始化表字段信息
     *
     * @param tableColumn 表字段信息
     */
    public static void initTableColumn(GenTableColumn tableColumn) {
        tableColumn.setJavaType(getJavaType(tableColumn.getColumnType()));
        tableColumn.setTsType(getTsType(tableColumn.getColumnType()));
        tableColumn.setJavaField(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,  tableColumn.getColumnName()));
        tableColumn.setIsList("1");
        tableColumn.setIsInsert(getIsInsert(tableColumn.getJavaField()));
        tableColumn.setIsEdit(getIsEdit(tableColumn));
        tableColumn.setHtmlType(getHtmlType(tableColumn.getColumnName()));
    }

    /**
     * 获取字段列表
     *
     * @param tableColumnList 字段列表
     * @return 字段列表
     */
    public static List<GenTableColumn> getTableColumnList(List<GenTableColumn> tableColumnList) {
        tableColumnList.forEach(tableColumn -> tableColumn.setIsSuperField(getIsSuperField(tableColumn.getJavaField())));
        return tableColumnList;
    }

    /**
     * 获取是否父类字段
     *
     * @param javaField java字段
     * @return 是否父类字段
     */
    private static boolean getIsSuperField(String javaField) {
        return List.of(SUPER_FIELD).contains(javaField);
    }

    /**
     * 获取 HTML 类型
     *
     * @param columnName 字段名称
     * @return HTML 类型
     */
    private static String getHtmlType(String columnName) {
        var htmlSelect = List.of("type");
        var htmlRadio = List.of("status");
        var htmlDate = List.of("time", "date");

        var htmlType = "input";
        // 如果包含 htmlRadio 返回 radio
        if (htmlRadio.stream().anyMatch(columnName::contains)) {
            htmlType = "radio";
        }
        // 如果包含 htmlSelect 返回 select
        if (htmlSelect.stream().anyMatch(columnName::contains)) {
            htmlType = "select";
        }
        // 如果包含 htmlDate 返回 date
        if (htmlDate.stream().anyMatch(columnName::contains)) {
            htmlType = "date";
        }
        return htmlType;
    }

    /**
     * 获取是否允许编辑
     *
     * @param tableColumn 表字段信息
     * @return 是否允许编辑
     */
    private static String getIsEdit(GenTableColumn tableColumn) {
        return List.of(NOT_EDIT_FIELD).contains(tableColumn.getJavaField()) || "1".equals(tableColumn.getIsPk()) ? "0" : "1";
    }

    /**
     * 获取是否允许插入
     *
     * @param javaField java 字段
     * @return 是否允许插入
     */
    private static String getIsInsert(String javaField) {
        return List.of(NOT_INSERT_FIELD).contains(javaField) ? "0" : "1";
    }

    /**
     * 获取 java 类型
     *
     * @param columnType 字段类型
     * @return java 类型
     */
    private static String getJavaType(String columnType) {
        if (List.of(STRING_TYPE).stream().anyMatch(columnType::startsWith)) {
            return "String";
        }
        if (List.of(NUMBER_TYPE).contains(columnType)) {
            return "Long";
        }
        if (List.of(DATE_TYPE).contains(columnType)) {
            return "LocalDateTime";
        }
        return "";
    }

    /**
     * 获取 typescript 类型
     *
     * @param columnType 字段类型
     * @return typescript 类型
     */
    public static String getTsType(String columnType) {
        if (List.of(STRING_TYPE).stream().anyMatch(columnType::startsWith)) {
            return "string";
        }
        if (List.of(NUMBER_TYPE).contains(columnType)) {
            return "number";
        }
        if (List.of(DATE_TYPE).contains(columnType)) {
            return "Date";
        }
        return "";
    }

}
