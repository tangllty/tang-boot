package com.tang.system.service;

import java.util.List;
import java.util.Set;

import com.tang.commons.utils.tree.TreeSelect;
import com.tang.system.entity.SysRole;

/**
 * 角色表 SysRole 表服务接口
 *
 * @author Tang
 */
public interface SysRoleService {

    /**
     * 获取角色列表
     *
     * @param role 角色对象
     * @return 角色列表
     */
    List<SysRole> selectRoleList(SysRole role);

    /**
     * 通过主键查询单条数据
     *
     * @param roleId 主键
     * @return 角色对象
     */
    SysRole selectRoleByRoleId(Long roleId);

    /**
     * 根据用户主键获取角色集合
     *
     * @param userId 用户主键
     * @return 角色集合
     */
    Set<String> getRolesByUserId(Long userId);

    /**
     * 获取角色下拉框数据
     *
     * @return 角色下拉框数据
     */
    List<TreeSelect> selectRolesSelect();

    /**
     * 新增角色
     *
     * @param role 角色对象
     * @return 影响行数
     */
    int insertRole(SysRole role);

    /**
     * 修改角色信息
     *
     * @param role 角色对象
     * @return 影响行数
     */
    int updateRoleByRoleId(SysRole role);

    /**
     * 修改角色状态
     *
     * @param role 角色对象
     * @return 影响行数
     */
    int updateRoleStatusByRoleId(SysRole role);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    int deleteRoleByRoleId(Long roleId);

    /**
     * 批量删除角色
     *
     * @param roleIds 角色主键集合
     * @return 影响行数
     */
    int deleteRoleByRoleIds(Long[] roleIds);

}
