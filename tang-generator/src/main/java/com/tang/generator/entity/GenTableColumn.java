package com.tang.generator.entity;

import com.tang.commons.base.entity.BaseEntity;

/**
 * 代码字段生成表 gen_table_column 实体类
 *
 * @author Tang
 */
public class GenTableColumn extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = 2148893922489984601L;

    /**
     * 编号
     */
    private Long columnId;

    /**
     * 归属表编号
     */
    private Long tableId;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段描述
     */
    private String columnComment;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA属性
     */
    private String javaField;

    /**
     * 是否主键{0=否, 1=是}
     */
    private String isPk;

    /**
     * 是否自增{0=否, 1=是}
     */
    private String isIncrement;

    /**
     * 是否列表字段{0=否, 1=是}
     */
    private String isList;

    /**
     * 是否为插入字段{0=否, 1=是}
     */
    private String isInsert;

    /**
     * 是否编辑字段{0=否, 1=是}
     */
    private String isEdit;

    /**
     * 是否必填{0=否, 1=是}
     */
    private String isRequired;

    /**
     * 查询方式{equal=等于, fuzzy=模糊}
     */
    private String queryType;

    /**
     * 显示类型
     */
    private String htmlType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 是否为父类字段
     */
    private boolean isSuperField;


    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaField() {
        return javaField;
    }

    public void setJavaField(String javaField) {
        this.javaField = javaField;
    }

    public String getIsPk() {
        return isPk;
    }

    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }

    public String getIsIncrement() {
        return isIncrement;
    }

    public void setIsIncrement(String isIncrement) {
        this.isIncrement = isIncrement;
    }

    public String getIsList() {
        return isList;
    }

    public void setIsList(String isList) {
        this.isList = isList;
    }

    public String getIsInsert() {
        return isInsert;
    }

    public void setIsInsert(String isInsert) {
        this.isInsert = isInsert;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getHtmlType() {
        return htmlType;
    }

    public void setHtmlType(String htmlType) {
        this.htmlType = htmlType;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean getIsSuperField() {
        return isSuperField;
    }

    public void setIsSuperField(boolean isSuperField) {
        this.isSuperField = isSuperField;
    }

}
