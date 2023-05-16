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
import com.tang.system.entity.SysMenu;
import com.tang.system.service.SysMenuService;

/**
 * 菜单权限逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    private final SysMenuService menuService;

    public SysMenuController(SysMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 获取菜单列表
     *
     * @param menu 菜单对象
     * @return 菜单列表
     */
    @PreAuthorize("@auth.hasPermission('system:menu:list')")
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu){
        var list = menuService.selectMenuListTree(menu);
        return AjaxResult.success(list);
    }

    /**
     * 获取菜单树下拉选项
     *
     * @param menu 菜单对象
     * @return 菜单树下拉选项
     */
    @PreAuthorize("@auth.hasPermission('system:menu:list')")
    @GetMapping("/menuTree")
    public AjaxResult menuTree(SysMenu menu){
        var list = menuService.selectMenuTree(menu);
        return AjaxResult.success(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param menuId 主键
     * @return 菜单对象
     */
    @PreAuthorize("@auth.hasPermission('system:menu:list')")
    @GetMapping("/{menuId}")
    public AjaxResult selectMenuByMenuId(@PathVariable Long menuId) {
        return AjaxResult.success(menuService.selectMenuByMenuId(menuId));
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:menu:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysMenu menu) {
        return AjaxResult.success(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单信息
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:menu:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysMenu menu) {
        return AjaxResult.success(menuService.updateMenuByMenuId(menu));
    }

    /**
     * 修改菜单状态
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:menu:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysMenu menu) {
        return AjaxResult.success(menuService.updateMenuStatusByMenuId(menu));
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:menu:delete')")
    @DeleteMapping("/{menuId}")
    public AjaxResult delete(@PathVariable Long menuId) {
        if (menuService.checkHasChildren(menuId)) {
            return AjaxResult.error("删除失败，存在下级部门");
        }
        return AjaxResult.success(menuService.deleteMenuByMenuId(menuId));
    }

}
