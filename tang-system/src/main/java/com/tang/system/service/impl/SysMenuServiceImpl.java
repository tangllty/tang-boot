package com.tang.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.commons.core.vo.MetaVo;
import com.tang.commons.core.vo.RouteVo;
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
            .peek(o -> o.setChildren(getChildrenList(menuList, o)))
            .toList();
        if (list.isEmpty() && !menuList.isEmpty()) {
            return Collections.emptyList();
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
            .peek(menu -> menu.setChildren(getChildrenList(menuList, menu)))
            .toList();
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
     * 根据用户主键获取菜单树
     *
     * @param userId 用户主键
     * @return 菜单树
     */
    public List<RouteVo> selectMenuListTreeByUserId(Long userId) {
        var menuList = menuMapper.selectMenuListByUserId(userId);
        var list = menuList.stream()
            .filter(o -> o.getParentId() == 0)
            .peek(o -> o.setChildren(getChildrenList(menuList, o)))
            .toList();
        if (list.isEmpty() && !menuList.isEmpty()) {
            return Collections.emptyList();
        }
        return buildRoutes(list);
    }

    /**
     * 构建路由菜单
     *
     * @param menuList 菜单列表
     * @return 路由列表
     */
    public List<RouteVo> buildRoutes(List<SysMenu> menuList) {
        return menuList.stream().map(menu -> {
            var children = menu.getChildren();
            var route = new RouteVo();
            var meta = new MetaVo();
            var menuType = menu.getMenuType();
            if ("D".equals(menuType)) {
                route.setName(menu.getPath());
                route.setPath("/" + menu.getPath());
                route.setComponent("Layout");
                if (StringUtils.isEmpty(route.getRedirect()) && children != null && !children.isEmpty()) {
                    route.setRedirect(route.getPath() + "/" + menu.getChildren().get(0).getPath());
                }
            }
            if ("M".equals(menuType)) {
                route.setPath(menu.getPath());
                route.setComponent(menu.getComponent());
            }
            meta.setTitle(menu.getMenuName());
            meta.setIcon(menu.getIcon());
            route.setMeta(meta);
            if (children != null && !children.isEmpty()) {
                route.setChildren(buildRoutes(children));
            }
            return route;
        }).collect(Collectors.toList());
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
