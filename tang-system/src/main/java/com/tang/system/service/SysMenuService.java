package com.tang.system.service;

import java.util.List;

import com.tang.commons.core.vo.RouteVo;
import com.tang.commons.utils.tree.TreeSelect;
import com.tang.system.entity.SysMenu;

/**
 * 菜单权限表 SysMenu 表服务接口
 *
 * @author Tang
 */
public interface SysMenuService {

    /**
     * 获取菜单列表
     *
     * @param menu 菜单对象
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * 获取菜单树
     *
     * @param menu 菜单对象
     * @return 菜单树
     */
    List<SysMenu> selectMenuListTree(SysMenu menu);

    /**
     * 获取菜单树下拉选项
     *
     * @param menu 菜单对象
     * @return 菜单树下拉选项
     */
    List<TreeSelect> selectMenuTree(SysMenu menu);

    /**
     * 通过主键查询单条数据
     *
     * @param menuId 主键
     * @return 菜单对象
     */
    SysMenu selectMenuByMenuId(Long menuId);

    /**
     * 根据用户主键获取菜单树
     *
     * @param userId 用户主键
     * @return 菜单树
     */
    List<RouteVo> selectMenuListTreeByUserId(Long userId);

    /**
     * 构建路由菜单
     *
     * @param menuList 菜单列表
     * @return 路由列表
     */
    List<RouteVo> buildRoutes(List<SysMenu> menuList);

    /**
     * 新增菜单
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    int insertMenu(SysMenu menu);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    int updateMenuByMenuId(SysMenu menu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteMenuByMenuId(Long menuId);

    /**
     * 是否含有子菜单
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    boolean checkHasChildren(Long menuId);

}
