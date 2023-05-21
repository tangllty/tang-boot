package com.tang.commons.core.model;

import java.io.Serializable;

/**
 * 字典数据模型
 *
 * @author Tang
 */
public class SysDictDataModel implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = -6924717173265014164L;

    /**
     * 数据ID
     */
    private Long dataId;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String dataLabel;

    /**
     * 字典键值
     */
    private String dataValue;

    /**
     * 样式属性
     */
    private String cssClass;

    /**
     * 类型样式
     */
    private String typeClass;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态{0=正常, 1=停用}
     */
    private String status;

    /**
     * 备注
     */
    private String remark;


    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
