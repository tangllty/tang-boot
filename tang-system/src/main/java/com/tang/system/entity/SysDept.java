package com.tang.system.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.tang.commons.core.base.entity.BaseEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 部门表 sys_dept 实体类
 *
 * @author Tang
 */
public class SysDept extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = -6834560732408723213L;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 父部门id
     */
    @NotNull(message = "父部门不能为空")
    @Min(value = 1, message = "父部门编号不能小于1")
    @Max(value = Long.MAX_VALUE, message = "父部门编号不能超过" + Long.MAX_VALUE)
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    @Length(min = 2, max = 32, message = "部门名称长度应在 2 到 32 之间")
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 部门状态
     */
    private String status;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 子部门
     */
    private List<SysDept> children;


    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public List<SysDept> getChildren() {
        return children;
    }

    public void setChildren(List<SysDept> children) {
        this.children = children;
    }

}
