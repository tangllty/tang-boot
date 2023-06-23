package com.tang.system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.page.PageUtils;
import com.tang.commons.utils.page.TableDataResult;
import com.tang.system.entity.SysRole;
import com.tang.system.service.SysRoleService;

import jakarta.validation.Valid;

/**
 * 角色逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    private final SysRoleService roleService;

    public SysRoleController(SysRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 获取角色列表
     *
     * @param role 角色对象
     * @return 角色列表
     */
    @PreAuthorize("@auth.hasPermission('system:role:list')")
    @GetMapping("/list")
    public TableDataResult list(SysRole role){
        PageUtils.startPage();
        var list = roleService.selectRoleList(role);
        return PageUtils.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param roleId 主键
     * @return 角色对象
     */
    @PreAuthorize("@auth.hasPermission('system:role:list')")
    @GetMapping("/{roleId}")
    public AjaxResult selectRoleByRoleId(@PathVariable Long roleId) {
        return AjaxResult.success(roleService.selectRoleByRoleId(roleId));
    }

    /**
     * 新增角色
     *
     * @param role 角色对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:role:add')")
    @PostMapping
    public AjaxResult add(@Valid @RequestBody SysRole role) {
        return AjaxResult.rows(roleService.insertRole(role));
    }

    /**
     * 修改角色信息
     *
     * @param role 角色对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:role:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysRole role) {
        return AjaxResult.rows(roleService.updateRoleByRoleId(role));
    }

    /**
     * 修改角色状态
     *
     * @param role 角色对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:role:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysRole role) {
        return AjaxResult.rows(roleService.updateRoleStatusByRoleId(role));
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:role:delete')")
    @DeleteMapping("/{roleId}")
    public AjaxResult delete(@PathVariable Long roleId) {
        return AjaxResult.rows(roleService.deleteRoleByRoleId(roleId));
    }

    /**
     * 批量删除角色
     *
     * @param roleIds 角色主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:role:delete')")
    @DeleteMapping
    public AjaxResult deletes(@RequestBody Long[] roleIds) {
        return AjaxResult.rows(roleService.deleteRoleByRoleIds(roleIds));
    }

}
