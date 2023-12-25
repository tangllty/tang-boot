package com.tang.generator.entity;

import java.util.List;

import com.tang.commons.base.entity.BaseEntity;

/**
 * 代码生成表 gen_table 实体类
 *
 * @author Tang
 */
public class GenTable extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = -3015638165843009126L;

    /**
     * 编号
     */
    private Long tableId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 实体类名称
     */
    private String className;

    /**
     * 包路径
     */
    private String packageName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 业务名
     */
    private String businessName;

    /**
     * 类注释
     */
    private String classComment;

    /**
     * 作者
     */
    private String author;

    /**
     * 父菜单ID
     */
    private Long parentMenuId;

    /**
     * 表字段列表
     */
    private List<GenTableColumn> tableColumnList;


    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getClassComment() {
        return classComment;
    }

    public void setClassComment(String classComment) {
        this.classComment = classComment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public List<GenTableColumn> getTableColumnList() {
        return tableColumnList;
    }

    public void setTableColumnList(List<GenTableColumn> tableColumnList) {
        this.tableColumnList = tableColumnList;
    }

}
