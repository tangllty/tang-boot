package com.tang.system.service;

import java.util.List;

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
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    int deleteRoleByRoleId(Long roleId);

}
