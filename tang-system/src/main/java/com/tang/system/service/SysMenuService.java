package com.tang.system.service;

import java.util.List;

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
     * 通过主键查询单条数据
     *
     * @param menuId 主键
     * @return 菜单对象
     */
    SysMenu selectMenuByMenuId(Long menuId);

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

}
