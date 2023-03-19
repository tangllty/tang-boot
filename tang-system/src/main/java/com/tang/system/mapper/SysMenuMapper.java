package com.tang.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tang.system.entity.SysMenu;

/**
 * 菜单权限表 sys_menu 表数据库访问层
 *
 * @author Tang
 */
public interface SysMenuMapper {

    /**
     * 获取菜单列表
     *
     * @param menu 菜单对象
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * 查询子菜单
     *
     * @param menuId 菜单ID
     * @return 子菜单集合
     */
    List<SysMenu> selectMenuChildrenByMenuId(Long menuId);

    /**
     * 通过主键查询单条数据
     *
     * @param menuId 主键
     * @return 菜单对象
     */
    SysMenu selectMenuByMenuId(Long menuId);

    /**
     * 通过用户主键获取菜单列表
     *
     * @param userId 用户主键
     * @return 菜单列表
     */
    List<SysMenu> selectMenuListByUserId(Long userId);

    /**
     * 根据角色主键获取菜单列表
     *
     * @param roleId 用户主键
     * @return 菜单列表
     */
    List<SysMenu> selectMenuListByRoleId(Long roleId);

    /**
     * 新增菜单
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    int insertMenu(SysMenu menu);

    /**
     * 新增角色菜单关联信息
     *
     * @param roleId 角色主键
     * @param menuIds 菜单主键集合
     * @return 影响行数
     */
    int insertRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    int updateMenuByMenuId(SysMenu menu);

    /**
     * 修改子菜单信息
     *
     * @param children 子菜单对象
     * @return 影响行数
     */
    int updateMenuChildren(SysMenu children);

    /**
     * 修改菜单状态
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    int updateMenuStatusByMenuId(SysMenu menu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteMenuByMenuId(Long menuId);

    /**
     * 通过角色主键删除角色菜单关联信息
     *
     * @param roleId 角色主键
     * @return 影响行数
     */
    int deleteRoleMenuByRoleId(Long roleId);

    /**
     * 通过角色主键批量删除角色菜单关联信息
     *
     * @param roleIds 角色主键集合
     * @return 影响行数
     */
    int deleteRoleMenuByRoleIds(Long[] roleIds);

}
