package com.tang.system.entity;

import java.util.List;

import com.tang.commons.core.base.entity.BaseEntity;

/**
 * 角色表 sys_role 实体类
 *
 * @author Tang
 */
public class SysRole extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = -1878026743787136232L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 数据范围
     */
    private String dataScope;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 角色状态
     */
    private String status;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 菜单权限
     */
    private List<Long> menuIds;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
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

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

}

