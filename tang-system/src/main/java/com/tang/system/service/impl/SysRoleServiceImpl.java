package com.tang.system.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.commons.utils.tree.TreeSelect;
import com.tang.system.entity.SysMenu;
import com.tang.system.entity.SysRole;
import com.tang.system.mapper.SysMenuMapper;
import com.tang.system.mapper.SysRoleMapper;
import com.tang.system.service.SysRoleService;

/**
 * 角色表 SysRole 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper roleMapper;

    private final SysMenuMapper menuMapper;

    public SysRoleServiceImpl(SysRoleMapper roleMapper, SysMenuMapper menuMapper) {
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
    }

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
        var role = roleMapper.selectRoleByRoleId(roleId);
        var menuList = menuMapper.selectMenuListByRoleId(roleId);
        var menuIds = menuList.stream().map(SysMenu::getMenuId).toList();
        role.setMenuIds(menuIds);
        return role;
    }

    /**
     * 根据用户主键获取角色集合
     *
     * @param userId 用户主键
     * @return 角色集合
     */
    @Override
    public Set<String> getRolesByUserId(Long userId) {
        var roleList = roleMapper.selectRoleListByUserId(userId);
        return roleList.stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
    }

    /**
     * 根据用户主键获取角色主键集合
     *
     * @param userId 用户主键
     * @return 角色主键集合
     */
    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        var roleList = roleMapper.selectRoleListByUserId(userId);
        return roleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
    }

    /**
     * 获取角色下拉框数据
     *
     * @return 角色下拉框数据
     */
    @Override
    public List<TreeSelect> selectRolesSelect() {
        var roleList = roleMapper.selectRoleList(null);
        return roleList.stream().map(role -> new TreeSelect(role.getRoleId(), role.getRoleName())).toList();
    }

    /**
     * 新增角色
     *
     * @param role 角色对象
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role) {
        var rows = roleMapper.insertRole(role);
        var menuIds = role.getMenuIds();
        if (!menuIds.isEmpty()) {
            menuMapper.insertRoleMenu(role.getRoleId(), menuIds);
        }
        return rows;
    }

    /**
     * 修改角色信息
     *
     * @param role 角色对象
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRoleByRoleId(SysRole role) {
        var roleId = role.getRoleId();
        var menuIds = role.getMenuIds();
        menuMapper.deleteRoleMenuByRoleId(roleId);
        if (!menuIds.isEmpty()) {
            menuMapper.insertRoleMenu(roleId, menuIds);
        }
        return roleMapper.updateRoleByRoleId(role);
    }

    /**
     * 修改角色状态
     *
     * @param role 角色对象
     * @return 影响行数
     */
    @Override
    public int updateRoleStatusByRoleId(SysRole role) {
        return roleMapper.updateRoleStatusByRoleId(role);
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByRoleId(Long roleId) {
        menuMapper.deleteRoleMenuByRoleId(roleId);
        return roleMapper.deleteRoleByRoleId(roleId);
    }

    /**
     * 批量删除角色
     *
     * @param roleIds 角色主键集合
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByRoleIds(Long[] roleIds) {
        menuMapper.deleteRoleMenuByRoleIds(roleIds);
        return roleMapper.deleteRoleByRoleIds(roleIds);
    }

}
