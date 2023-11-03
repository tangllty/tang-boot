package com.tang.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.commons.constants.Status;
import com.tang.commons.domain.vo.MetaVo;
import com.tang.commons.domain.vo.RouteVo;
import com.tang.commons.enumeration.MenuType;
import com.tang.commons.utils.SecurityUtils;
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

    private final SysMenuMapper menuMapper;

    public SysMenuServiceImpl(SysMenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

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
        return  menuList.stream()
            .filter(o -> o.getParentId() == 0)
            .map(o -> {
                o.setChildren(getChildrenList(menuList, o));
                return o;
            }).toList();
    }

    /**
     * 获取子菜单列表
     *
     * @param menuList 菜单列表
     * @param parentMenu 上级菜单对象
     * @return 子菜单列表
     */
    private List<SysMenu> getChildrenList(List<SysMenu> menuList, SysMenu parentMenu) {
        return menuList.stream()
            .filter(menu -> Objects.equals(menu.getParentId(), parentMenu.getMenuId()))
            .map(menu -> {
                menu.setChildren(getChildrenList(menuList, menu));
                return menu;
            }).toList();
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
        menuList.forEach(m -> treeSelectList.add(new TreeSelect(m.getParentId(), m.getMenuId(), m.getMenuName())));
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
     * 根据用户主键获取权限集合
     *
     * @param userId 用户主键
     * @return 权限集合
     */
    @Override
    public Set<String> getPermissionsByUserId(Long userId) {
        var menuList = menuMapper.selectMenuListByUserId(userId);
        var permissions = menuList.stream().map(SysMenu::getPermission).collect(Collectors.toSet());
        permissions.removeIf(StringUtils::isEmpty);
        return permissions;
    }

    /**
     * 根据用户主键获取菜单树
     *
     * @param userId 用户主键
     * @return 菜单树
     */
    @Override
    public List<RouteVo> selectMenuListTreeByUserId(Long userId) {
        List<SysMenu> menuList;
        if (SecurityUtils.isAdmin()) {
            var menu = new SysMenu();
            menu.setStatus(Status.NORMAL);
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menuList = menuMapper.selectMenuListByUserId(userId);
        }
        menuList.removeIf(menu -> MenuType.BUTTON.getName().equals(menu.getMenuType()));
        var list = menuList.stream()
            .filter(m -> m.getParentId() == 0)
            .map(m -> {
                m.setChildren(getChildrenList(menuList, m));
                return m;
            })
            .toList();
        return buildRoutes(list);
    }

    /**
     * 构建路由菜单
     *
     * @param menuList 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouteVo> buildRoutes(List<SysMenu> menuList) {
        return buildRoutes(menuList, menuList);
    }

    /**
     * 构建路由菜单
     *
     * @param menuList         菜单列表
     * @param originalMenuList 原始菜单列表
     * @return 路由列表
     */
    private List<RouteVo> buildRoutes(List<SysMenu> menuList, List<SysMenu> originalMenuList) {
        return menuList.stream().map(menu -> {
            var children = menu.getChildren();
            var route = new RouteVo();
            var meta = new MetaVo();
            var menuType = menu.getMenuType();
            if (menuType.equals(MenuType.DIRECTORY.getName())) {
                route.setName(menu.getPath());
                route.setPath(menu.getPath());
                if (menu.getParentId() == 0) {
                    route.setPath("/" + menu.getPath());
                    route.setComponent("Layout");
                }
                if (CollectionUtils.isNotEmpty(children)) {
                    setRedirect(originalMenuList, menu, route);
                }
            }
            if (menuType.equals(MenuType.MENU.getName())) {
                route.setPath(menu.getPath());
                route.setComponent(menu.getComponent());
            }
            meta.setTitle(menu.getMenuName());
            meta.setIcon(menu.getIcon());
            route.setMeta(meta);
            if (CollectionUtils.isNotEmpty(children)) {
                route.setChildren(buildRoutes(children, originalMenuList));
            }
            return route;
        }).toList();
    }

    /**
     * 设置重定向
     *
     * @param originalMenuList 原始菜单列表
     * @param menu             菜单对象
     * @param route            路由对象
     */
    private void setRedirect(List<SysMenu> originalMenuList, SysMenu menu, RouteVo route) {
        route.setRedirect(route.getPath() + "/" + menu.getChildren().get(0).getPath());
        if (!route.getPath().startsWith("/")) {
            var parentPath = getParentPath(originalMenuList, menu.getParentId());
            route.setRedirect(parentPath + "/" + route.getRedirect());
        }
    }

    /**
     * 获取父级路径
     *
     * @param originalMenuList 原始菜单列表
     * @param parentId         父级主键
     * @return 父级路径
     */
    private String getParentPath(List<SysMenu> originalMenuList, Long parentId) {
        var parentPath = new StringBuilder();
        for (var menu : originalMenuList) {
            if (menu.getMenuId().equals(parentId)) {
                parentPath.append("/").append(menu.getPath());
            }
            var children = menu.getChildren();
            if (CollectionUtils.isNotEmpty(children)) {
                parentPath.append(getParentPath(menu.getChildren(), parentId));
            }
        }
        return parentPath.toString();
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @Override
    public int insertMenu(SysMenu menu) {
        var ancestors = "0";
        if (menu.getParentId() != 0) {
            var parentId = menu.getParentId();
            var parentMenu = selectMenuByMenuId(parentId);
            ancestors = parentMenu.getAncestors() + "," + parentId;
        }
        menu.setAncestors(ancestors);
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改菜单信息
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMenuByMenuId(SysMenu menu) {
        var newMenu = menuMapper.selectMenuByMenuId(menu.getParentId());
        var oldMenu = menuMapper.selectMenuByMenuId(menu.getMenuId());
        if (Objects.nonNull(newMenu) && Objects.nonNull(oldMenu)) {
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
     * 修改菜单状态
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @Override
    public int updateMenuStatusByMenuId(SysMenu menu) {
        return menuMapper.updateMenuStatusByMenuId(menu);
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
