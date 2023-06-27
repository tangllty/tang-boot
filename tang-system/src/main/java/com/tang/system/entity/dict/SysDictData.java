package com.tang.system.entity.dict;

import org.hibernate.validator.constraints.Length;

import com.tang.commons.base.entity.BaseEntity;

import jakarta.validation.constraints.NotBlank;

/**
 * 字典数据表 sys_dict_data 实体类
 *
 * @author Tang
 * @since 2023-02-23 21:05:59
 */
public class SysDictData extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = -7824038855648355193L;

    /**
     * 数据ID
     */
    private Long dataId;

    /**
     * 字典类型
     */
    @Length(min = 4, max = 64, message = "字典类型长度应在 4 到 64 之间")
    private String dictType;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    private String dataLabel;

    /**
     * 字典键值
     */
    @NotBlank(message = "字典键值不能为空")
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

}
