package com.tang.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
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
 * 菜单权限表 SysMenu 表控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    /**
     * 服务对象
     */
    @Autowired
    private SysMenuService menuService;

    /**
     * 获取菜单列表
     *
     * @param menu 菜单对象
     * @return 菜单列表
     */
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu){
        var list = menuService.selectMenuListTree(menu);
        return AjaxResult.success(list);
    }

    /**
     * 获取菜单树下拉选项
     *
     * @param dept 菜单对象
     * @return 菜单树下拉选项
     */
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
    @GetMapping("/{menuId}")
    public AjaxResult selectMenuByMenuId(@PathVariable("menuId") Long menuId) {
        return AjaxResult.success(menuService.selectMenuByMenuId(menuId));
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
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
    @PutMapping
    public AjaxResult edit(@RequestBody SysMenu menu) {
        return AjaxResult.success(menuService.updateMenuByMenuId(menu));
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    @DeleteMapping("/{menuId}")
    public AjaxResult delete(@PathVariable Long menuId) {
        if (menuService.checkHasChildren(menuId)) {
            return AjaxResult.error("删除失败，存在下级部门");
        }
        return AjaxResult.success(menuService.deleteMenuByMenuId(menuId));
    }

}

