package com.tang.generator.utils;

import com.google.common.base.CaseFormat;
import com.tang.commons.utils.Assert;
import com.tang.commons.utils.SpringUtils;
import com.tang.generator.autoconfigure.GeneratorProperties;
import com.tang.generator.entity.GenTable;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * 表工具类
 *
 * @author Tang
 */
public class TableUtils {

    private TableUtils() {
    }

    private static final GeneratorProperties GENERATOR_PROPERTIES = SpringUtils.getBean(GeneratorProperties.class);

    /**
     * 初始化表信息
     *
     * @param table 表信息
     */
    public static void initTable(@NonNull GenTable table) {
        Assert.isNull(table, "表信息不能为空");
        table.setClassName(getClassName(table.getTableName()));
        table.setPackageName(GENERATOR_PROPERTIES.getPackageName());
        table.setModuleName(getModuleName(GENERATOR_PROPERTIES.getPackageName()));
        table.setBusinessName(table.getModuleName());
        table.setClassComment(getTableComment(table.getTableComment()));
        table.setAuthor(GENERATOR_PROPERTIES.getAuthor());
    }

    /**
     * 获取类注释
     *
     * @param tableComment 表注释
     * @return 类注释
     */
    private static String getTableComment(String tableComment) {
        return tableComment.replaceFirst("表$", "");
    }

    /**
     * 获取模块名
     *
     * @param packageName 包路径
     * @return 模块名
     */
    private static String getModuleName(@NonNull String packageName) {
        Assert.isNull(packageName, "包路径不能为空");
        var packageNames = packageName.split("\\.");
        return packageNames[packageNames.length - 1];
    }

    /**
     * 获取类名称
     *
     * @param tableName 表名称
     * @return 类名称
     */
    private static String getClassName(String tableName) {
        var className = tableName;
        var prefixes = GENERATOR_PROPERTIES.getRemovePre().split(",");
        for (String prefix : prefixes) {
            if (className.startsWith(prefix)) {
                className = className.replaceFirst(prefix, "");
                break;
            }
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, className);
    }

}
