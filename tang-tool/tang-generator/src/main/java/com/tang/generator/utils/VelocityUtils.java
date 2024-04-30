package com.tang.generator.utils;

import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.tang.commons.utils.SpringUtils;
import com.tang.generator.autoconfigure.GeneratorProperties;
import com.tang.generator.entity.GenTable;
import com.tang.generator.entity.GenTableColumn;
import com.tang.generator.enumeration.LanguageType;
import com.tang.generator.enumeration.OrmType;

import static com.tang.commons.utils.StringUtils.format;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_ENTITY;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_MAPPER;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_SERVICE;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_SERVICE_IMPL;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_CONTROLLER;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATISFLEX_ENTITY;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATISFLEX_MAPPER;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATISFLEX_SERVICE;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATISFLEX_SERVICE_IMPL;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATISFLEX_CONTROLLER;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_PLUS_ENTITY;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_PLUS_MAPPER;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_PLUS_SERVICE;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_PLUS_SERVICE_IMPL;
import static com.tang.generator.constants.VmTemplate.VM_JAVA_MYBATIS_PLUS_CONTROLLER;
import static com.tang.generator.constants.VmTemplate.VM_KOTLIN_MYBATIS_ENTITY;
import static com.tang.generator.constants.VmTemplate.VM_KOTLIN_MYBATIS_MAPPER;
import static com.tang.generator.constants.VmTemplate.VM_KOTLIN_MYBATIS_SERVICE;
import static com.tang.generator.constants.VmTemplate.VM_KOTLIN_MYBATIS_SERVICE_IMPL;
import static com.tang.generator.constants.VmTemplate.VM_KOTLIN_MYBATIS_CONTROLLER;
import static com.tang.generator.constants.VmTemplate.VM_SQL_MENU;
import static com.tang.generator.constants.VmTemplate.VM_VUE_INDEX;
import static com.tang.generator.constants.VmTemplate.VM_VUE_INDEX_TS;
import static com.tang.generator.constants.VmTemplate.VM_VUE_TYPES;
import static com.tang.generator.constants.VmTemplate.VM_XML_MYBATIS_MAPPER;
import static com.tang.generator.constants.VmTemplate.VM_XML_MYBATIS_PLUS_MAPPER;

/**
 * Velocity 工具类
 *
 * @author Tang
 */
public class VelocityUtils {

    private VelocityUtils() {
    }

    /**
     * Java 路径
     */
    private static final String SOURCE_JAVA_PATH = "main/java";

    /**
     * Kotlin 路径
     */
    private static final String SOURCE_KOTLIN_PATH = "main/kotlin";

    /**
     * MyBatis 路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /**
     * Vue 路径
     */
    private static final String VUE_PATH = "src";

    private static final GeneratorProperties GENERATOR_PROPERTIES = SpringUtils.getBean(GeneratorProperties.class);

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable table) {
        var velocityContext = new VelocityContext();
        velocityContext.put("packageName", table.getPackageName());
        velocityContext.put("classComment", table.getClassComment());
        velocityContext.put("author", table.getAuthor());
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("ClassName", table.getClassName());
        velocityContext.put("className", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, table.getClassName()));
        velocityContext.put("primaryKey", getPrimaryKey(table));
        velocityContext.put("PrimaryKey", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, getPrimaryKey(table)));
        velocityContext.put("tablePrimaryKey", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, getPrimaryKey(table)));
        velocityContext.put("columnList", TableColumnUtils.getTableColumnList(table.getTableColumnList()));
        velocityContext.put("moduleName", table.getModuleName());
        velocityContext.put("businessName", table.getBusinessName());
        velocityContext.put("permissionName", table.getBusinessName().replace("/", ":"));
        velocityContext.put("tableAlias", getTableAlias(table.getTableName()));
        velocityContext.put("hasDictType", getHasDictType(table));
        velocityContext.put("dictTypeVar", getDictTypeVar(table));
        velocityContext.put("dictTypeParams", getDictTypeParams(table));
        return velocityContext;
    }

    /**
     * 获取字典参数
     *
     * @param table 表信息
     * @return 字典参数
     */
    private static String getDictTypeParams(GenTable table) {
        var dictTypeParams = new StringBuilder();
        var dictTypeList = getDictTypeList(table);
        for (int i = 0; i < dictTypeList.size(); i++) {
            dictTypeParams.append("'").append(dictTypeList.get(i)).append("'");
            if (i < dictTypeList.size() - 1) {
                dictTypeParams.append(", ");
            }
        }
        return dictTypeParams.toString();
    }

    /**
     * 获取字典变量
     *
     * @param table 表信息
     * @return 字典变量
     */
    private static String getDictTypeVar(GenTable table) {
        var dictTypeVar = new StringBuilder();
        var dictTypeList = getDictTypeList(table);
        for (int i = 0; i < dictTypeList.size(); i++) {
            dictTypeVar.append(dictTypeList.get(i));
            if (i < dictTypeList.size() - 1) {
                dictTypeVar.append(", ");
            }
        }
        return dictTypeVar.toString();
    }

    /**
     * 获取字典集合
     *
     * @param table 表信息
     * @return 字典集合
     */
    private static List<String> getDictTypeList(GenTable table) {
        return table.getTableColumnList().stream()
            .map(GenTableColumn::getDictType)
            .filter(StringUtils::isNotBlank)
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * 获取是否包含字典类型
     *
     * @param table 表信息
     * @return 是否包含字典类型
     */
    private static boolean getHasDictType(GenTable table) {
        for (GenTableColumn tableColumn : table.getTableColumnList()) {
            if (StringUtils.isNotBlank(tableColumn.getDictType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取表别名
     *
     * @param tableName 表名
     * @return 表别名
     */
    private static String getTableAlias(String tableName) {
        var prefixedTableName = tableName;
        var prefixes = GENERATOR_PROPERTIES.getRemovePre().split(",");
        for (String prefix : prefixes) {
            if (prefixedTableName.startsWith(prefix)) {
                prefixedTableName = prefixedTableName.replaceFirst(prefix, "");
                break;
            }
        }
        var tableAlias = new StringWriter();
        Stream.of(prefixedTableName.split("_")).forEach(name -> tableAlias.append(name.charAt(0)));
        return tableAlias.toString();
    }

    /**
     * 获取主键字段
     *
     * @param table 表信息
     * @return 主键字段
     */
    private static String getPrimaryKey(GenTable table) {
        final var isPrimaryKey = "1";
        var primaryKey = "";
        for (GenTableColumn column : table.getTableColumnList()) {
            if (isPrimaryKey.equals(column.getIsPk())) {
                primaryKey = column.getJavaField();
            }
        }
        return primaryKey;
    }

    /**
     * 获取模板信息
     *
     * @param ormType ORM 类型
     * @return 模板列表
     */
    public static List<String> getTemplateList(String languageType, String ormType) {
        var templateList = List.of(VM_VUE_INDEX, VM_VUE_INDEX_TS, VM_VUE_TYPES, VM_SQL_MENU);
        var languageTypeEnum = LanguageType.getLanguageType(languageType);
        var ormTypeEnum = OrmType.getOrmType(ormType);
        if (Objects.isNull(languageTypeEnum) || Objects.isNull(ormTypeEnum)) {
            return templateList;
        }
        var javaTemplateList = getJavaTemplateList(languageTypeEnum, ormTypeEnum);
        return ImmutableList.<String>builder().addAll(javaTemplateList).addAll(templateList).build();
    }

    /**
     * 获取 Java 模板信息
     *
     * @param ormType ORM 类型
     * @return Java 模板列表
     */
    private static List<String> getJavaTemplateList(LanguageType languageType, OrmType ormType) {
        return switch (languageType) {
            case JAVA -> switch (ormType) {
                case MYBATIS -> List.of(VM_JAVA_MYBATIS_ENTITY, VM_JAVA_MYBATIS_MAPPER, VM_JAVA_MYBATIS_SERVICE, VM_JAVA_MYBATIS_SERVICE_IMPL, VM_JAVA_MYBATIS_CONTROLLER, VM_XML_MYBATIS_MAPPER);
                case MYBATIS_FLEX -> List.of(VM_JAVA_MYBATISFLEX_ENTITY, VM_JAVA_MYBATISFLEX_MAPPER, VM_JAVA_MYBATISFLEX_SERVICE, VM_JAVA_MYBATISFLEX_SERVICE_IMPL, VM_JAVA_MYBATISFLEX_CONTROLLER);
                case MYBATIS_PLUS -> List.of(VM_JAVA_MYBATIS_PLUS_ENTITY, VM_JAVA_MYBATIS_PLUS_MAPPER, VM_JAVA_MYBATIS_PLUS_SERVICE, VM_JAVA_MYBATIS_PLUS_SERVICE_IMPL, VM_JAVA_MYBATIS_PLUS_CONTROLLER, VM_XML_MYBATIS_PLUS_MAPPER);
                default -> Collections.emptyList();
            };
            case KOTLIN -> switch (ormType) {
                case MYBATIS -> List.of(VM_KOTLIN_MYBATIS_ENTITY, VM_KOTLIN_MYBATIS_MAPPER, VM_KOTLIN_MYBATIS_SERVICE, VM_KOTLIN_MYBATIS_SERVICE_IMPL, VM_KOTLIN_MYBATIS_CONTROLLER, VM_XML_MYBATIS_MAPPER);
                case MYBATIS_PLUS -> List.of(VM_KOTLIN_MYBATIS_ENTITY, VM_KOTLIN_MYBATIS_MAPPER, VM_KOTLIN_MYBATIS_SERVICE, VM_KOTLIN_MYBATIS_SERVICE_IMPL, VM_KOTLIN_MYBATIS_CONTROLLER, VM_XML_MYBATIS_PLUS_MAPPER);
                default -> Collections.emptyList();
            };
        };
    }

    /**
     * 获取文件名
     *
     * @param template 模板信息
     * @param table 表信息
     * @return 文件名
     */
    public static String getFileName(String template, GenTable table) {
        // 文件名称
        var fileName = "";
        // 包路径
        var packageName = table.getPackageName();
        // 模块名
        var moduleName = table.getModuleName();
        // 大写类名
        var className = table.getClassName();
        // 业务名称
        var businessName = table.getBusinessName();

        var pathTemplate = "{}/{}";
        var javaPath = format(pathTemplate, SOURCE_JAVA_PATH, packageName.replace(".", "/"));
        var kotlinPath = format(pathTemplate, SOURCE_KOTLIN_PATH, packageName.replace(".", "/"));
        var mybatisPath = format(pathTemplate, MYBATIS_PATH, moduleName);
        var vuePath = format("{}/views/{}", VUE_PATH, moduleName);
        var apiPath = format("{}/api/{}", VUE_PATH, moduleName);

        fileName = switch (template) {
            case VM_JAVA_MYBATIS_ENTITY, VM_JAVA_MYBATISFLEX_ENTITY, VM_JAVA_MYBATIS_PLUS_ENTITY -> format("{}/entity/{}.java", javaPath, className);
            case VM_JAVA_MYBATIS_MAPPER, VM_JAVA_MYBATISFLEX_MAPPER, VM_JAVA_MYBATIS_PLUS_MAPPER -> format("{}/mapper/{}Mapper.java", javaPath, className);
            case VM_JAVA_MYBATIS_SERVICE, VM_JAVA_MYBATISFLEX_SERVICE, VM_JAVA_MYBATIS_PLUS_SERVICE -> format("{}/service/{}Service.java", javaPath, className);
            case VM_JAVA_MYBATIS_SERVICE_IMPL, VM_JAVA_MYBATISFLEX_SERVICE_IMPL, VM_JAVA_MYBATIS_PLUS_SERVICE_IMPL -> format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
            case VM_JAVA_MYBATIS_CONTROLLER, VM_JAVA_MYBATISFLEX_CONTROLLER, VM_JAVA_MYBATIS_PLUS_CONTROLLER -> format("{}/controller/{}Controller.java", javaPath, className);
            case VM_KOTLIN_MYBATIS_ENTITY -> format("{}/entity/{}.kt", kotlinPath, className);
            case VM_KOTLIN_MYBATIS_MAPPER -> format("{}/mapper/{}Mapper.kt", kotlinPath, className);
            case VM_KOTLIN_MYBATIS_SERVICE -> format("{}/service/{}Service.kt", kotlinPath, className);
            case VM_KOTLIN_MYBATIS_SERVICE_IMPL -> format("{}/service/impl/{}ServiceImpl.kt", kotlinPath, className);
            case VM_KOTLIN_MYBATIS_CONTROLLER -> format("{}/controller/{}Controller.kt", kotlinPath, className);
            case VM_XML_MYBATIS_MAPPER, VM_XML_MYBATIS_PLUS_MAPPER -> format("{}/{}Mapper.xml", mybatisPath, className);
            case VM_VUE_INDEX -> format("{}/{}/index.vue", vuePath, businessName);
            case VM_VUE_INDEX_TS -> format("{}/{}/index.ts", apiPath, businessName);
            case VM_VUE_TYPES -> format("{}/{}/types.ts", apiPath, businessName);
            case VM_SQL_MENU -> "menu.sql";
            default -> template + "_" + System.currentTimeMillis();
        };
        return fileName;
    }

}
