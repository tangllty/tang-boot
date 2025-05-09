package com.tang.system.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.tang.commons.base.entity.BaseEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 菜单权限表 sys_menu 实体类
 *
 * @author Tang
 * @since 2022-12-14 07:22:39
 */
public class SysMenu extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = -3559258252404112781L;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 父菜单ID
     */
    @NotNull(message = "父菜单不能为空")
    @Min(value = 0, message = "父菜单编号不能小于0")
    @Max(value = Long.MAX_VALUE, message = "父菜单编号不能超过" + Long.MAX_VALUE)
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 菜单名称
     */
    @Length(min = 2, max = 32, message = "菜单名称长度应在 2 到 32 之间")
    private String menuName;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单类型（M目录 C菜单 B按钮 P页面）
     */
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 子菜单
     */
    private List<SysMenu> children;


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
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

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

}
