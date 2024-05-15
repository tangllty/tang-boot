package com.tang.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tang.system.entity.SysRole;

/**
 * 角色表 sys_role 表数据库访问层
 *
 * @author Tang
 */
@Mapper
public interface SysRoleMapper {

    /**
     * 获取角色列表
     *
     * @param role 角色对象
     * @return 角色列表
     */
    List<SysRole> selectRoleList(SysRole role);

    /**
     * 根据主键查询单条数据
     *
     * @param roleId 主键
     * @return 角色对象
     */
    SysRole selectRoleByRoleId(Long roleId);

    /**
     * 根据用户主键获取角色列表
     *
     * @param userId 用户主键
     * @return 角色列表
     */
    List<SysRole> selectRoleListByUserId(Long userId);

    /**
     * 新增角色
     *
     * @param role 角色对象
     * @return 影响行数
     */
    int insertRole(SysRole role);

    /**
     * 新增用户角色关联信息
     *
     * @param userId 用户主键
     * @param roleIds 角色主键集合
     * @return 影响行数
     */
    int insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

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
     * 通过用户主键删除角色关联数据
     *
     * @param userId 用户主键
     * @return 影响行数
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * 通过用户主键数组批量删除用户角色关联数据
     *
     * @param userIds 用户主键数组
     * @return 影响行数
     */
    int deleteUserRoleByUserIds(Long[] userIds);

    /**
     * 通过角色主键数组批量删除角色关联数据
     *
     * @param roleIds 角色主键数组
     * @return 影响行数
     */
    int deleteRoleByRoleIds(Long[] roleIds);

}
