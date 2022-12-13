package com.tang.system.service.impl;

import com.tang.system.entity.SysDept;
import com.tang.system.entity.SysRole;
import com.tang.system.mapper.SysRoleMapper;
import com.tang.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表 SysRole 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 获取角色列表
     *
     * @param role 角色对象
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param roleId 主键
     * @return 角色对象
     */
    @Override
    public SysRole selectRoleByRoleId(Long roleId) {
        return roleMapper.selectRoleByRoleId(roleId);
    }

    /**
     * 新增角色
     *
     * @param role 角色对象
     * @return 影响行数
     */
    @Override
    public int insertRole(SysRole role) {
        return roleMapper.insertRole(role);
    }

    /**
     * 修改角色信息
     *
     * @param role 角色对象
     * @return 影响行数
     */
    @Override
    public int updateRoleByRoleId(SysRole role) {
        return roleMapper.updateRoleByRoleId(role);
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    @Override
    public int deleteRoleByRoleId(Long roleId) {
        return roleMapper.deleteRoleByRoleId(roleId);
    }

}
