package com.tang.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.commons.utils.tree.TreeSelect;
import com.tang.commons.utils.tree.TreeUtils;
import com.tang.system.entity.SysMenu;
import com.tang.system.mapper.SysMenuMapper;
import com.tang.system.service.SysMenuService;

/**
 * 菜单权限表 SysMenu 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    /**
     * 获取菜单列表
     *
     * @param menu 菜单对象
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu) {
        return menuMapper.selectMenuList(menu);
    }

    /**
     * 获取菜单树
     *
     * @param menu 菜单对象
     * @return 菜单树
     */
    @Override
    public List<SysMenu> selectMenuListTree(SysMenu menu) {
        var menuList = menuMapper.selectMenuList(menu);
        var list = menuList.stream()
            .filter(o -> o.getParentId() == 0)
            .map(o -> {
                o.setChildren(getChildrenList(menuList, o));
                return o;
            }).collect(Collectors.toList());
        if (list.isEmpty() && !menuList.isEmpty()) {
            return menuList;
        }
        return list;
    }

    /**
     * 获取子菜单列表
     *
     * @param menuList 菜单列表
     * @param parentMenu 上级菜单对象
     * @return 子菜单列表
     */
    private List<SysMenu> getChildrenList(List<SysMenu> menuList, SysMenu parentMenu) {
        var childrenList = menuList.stream()
            .filter(menu -> Objects.equals(menu.getParentId(), parentMenu.getMenuId()))
            .map(menu -> {
                menu.setChildren(getChildrenList(menuList, menu));
                return menu;
            }).collect(Collectors.toList());
        return childrenList;
    }

    /**
     * 获取菜单树下拉选项
     *
     * @param menu 菜单对象
     * @return 菜单树下拉选项
     */
    @Override
    public List<TreeSelect> selectMenuTree(SysMenu menu) {
        var menuList = menuMapper.selectMenuList(menu);
        var treeSelectList = new ArrayList<TreeSelect>();
        menuList.forEach(o -> treeSelectList.add(new TreeSelect(o.getParentId(), o.getMenuId(), o.getMenuName())));
        return TreeUtils.buildTree(treeSelectList);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param menuId 主键
     * @return 菜单对象
     */
    @Override
    public SysMenu selectMenuByMenuId(Long menuId) {
        return menuMapper.selectMenuByMenuId(menuId);
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @Override
    public int insertMenu(SysMenu menu) {
        var parentId = menu.getParentId();
        var parentMenu = selectMenuByMenuId(parentId);
        menu.setAncestors(parentMenu.getAncestors() + "," + parentId);
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改菜单信息
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @Override
    public int updateMenuByMenuId(SysMenu menu) {
        var newMenu = menuMapper.selectMenuByMenuId(menu.getParentId());
        var oldMenu = menuMapper.selectMenuByMenuId(menu.getMenuId());
        if (newMenu != null && oldMenu != null) {
            var newAncestors = newMenu.getAncestors() + "," + newMenu.getMenuId();
            var oldAncestors = oldMenu.getAncestors();
            menu.setAncestors(newAncestors);
            var menuId = menu.getMenuId();
            var childrenList = menuMapper.selectMenuChildrenByMenuId(menuId);
            if (!childrenList.isEmpty()) {
                childrenList.forEach(children -> {
                    children.setAncestors(children.getAncestors().replaceFirst(oldAncestors, newAncestors));
                    menuMapper.updateMenuChildren(children);
                });
            }
        }
        return menuMapper.updateMenuByMenuId(menu);
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    @Override
    public int deleteMenuByMenuId(Long menuId) {
        return menuMapper.deleteMenuByMenuId(menuId);
    }

    /**
     * 是否含有子菜单
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkHasChildren(Long menuId) {
        var childrenList = menuMapper.selectMenuChildrenByMenuId(menuId);
        return !childrenList.isEmpty();
    }

}
