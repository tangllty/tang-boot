package com.tang.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.commons.utils.SecurityUtils;
import com.tang.system.entity.SysRole;
import com.tang.system.entity.SysUser;
import com.tang.system.mapper.SysRoleMapper;
import com.tang.system.mapper.SysUserMapper;
import com.tang.system.service.SysUserService;

/**
 * 用户表 SysUser 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 获取用户列表
     *
     * @param user 用户对象
     * @return 用户列表
     */
    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 用户对象
     */
    @Override
    public SysUser selectUserByUserId(Long userId) {
        var user = userMapper.selectUserByUserId(userId);
        var roleList = roleMapper.selectRoleListByUserId(userId);
        var roleIds = roleList.stream().map(SysRole::getRoleId).toList();
        user.setRoleIds(roleIds);
        return user;
    }

    /**
     * 根据用户名查询单条数据
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public SysUser selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    /**
     * 根据邮箱查询单条数据
     *
     * @param email 邮箱
     * @return 用户对象
     */
    @Override
    public SysUser selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUser user) {
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        var rows = userMapper.insertUser(user);
        var roleIds = user.getRoleIds();
        roleMapper.insertUserRole(user.getUserId(), roleIds);
        return rows;
    }

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserByUserId(SysUser user) {
        roleMapper.deleteUserRoleByUserId(user.getUserId());
        var roleIds = user.getRoleIds();
        roleMapper.insertUserRole(user.getUserId(), roleIds);
        return userMapper.updateUserByUserId(user);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户对象
     * @return 影响行数
     */
    @Override
    public int updateUserStatusByUserId(SysUser user) {
        return userMapper.updateUserStatusByUserId(user);
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByUserId(Long userId) {
        roleMapper.deleteUserRoleByUserId(userId);
        return userMapper.deleteUserByUserId(userId);
    }

}
