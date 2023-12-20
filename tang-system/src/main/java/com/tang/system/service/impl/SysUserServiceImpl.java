package com.tang.system.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.commons.domain.vo.PasswordVo;
import com.tang.commons.exception.user.EmailNotUniqueException;
import com.tang.commons.exception.user.PasswordMismatchException;
import com.tang.commons.exception.user.UserNotFoundException;
import com.tang.commons.exception.user.UsernameNotUniqueException;
import com.tang.commons.utils.Assert;
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

    private final SysUserMapper userMapper;

    private final SysRoleMapper roleMapper;

    public SysUserServiceImpl(SysUserMapper userMapper, SysRoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

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
     * 模糊查询用户列表
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @Override
    public List<SysUser> selectUserListFuzzy(String keyword) {
        var userId = SecurityUtils.getUser().getUserId();
        return userMapper.selectUserListFuzzy(userId, keyword);
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
        var username = user.getUsername();
        Assert.isTrue(StringUtils.isBlank(username), new UsernameNotUniqueException("新增失败, 用户名不能为空"));
        Assert.nonNull(selectUserByUsername(username), new UsernameNotUniqueException("新增失败, 用户名已存在: " + username));

        var email = user.getEmail();
        Assert.isTrue(StringUtils.isBlank(email), new EmailNotUniqueException("新增失败, 邮箱不能为空"));
        Assert.nonNull(selectUserByEmail(email), new EmailNotUniqueException("新增失败, 邮箱已存在: " + email));

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
     * 修改用户密码
     *
     * @param passwordVo 密码对象
     * @return 影响行数
     */
    @Override
    public int updatePasswordByUserId(PasswordVo passwordVo) {
        var user = userMapper.selectUserByUserId(passwordVo.getUserId());

        Assert.isNull(user, new UserNotFoundException("用户不存在"));
        Assert.isFalse(SecurityUtils.matchesPassword(passwordVo.getOldPassword(), user.getPassword()), new PasswordMismatchException("旧密码错误"));
        Assert.isTrue(passwordVo.getNewPassword().equals(passwordVo.getOldPassword()), new PasswordMismatchException("新密码不能与旧密码相同"));
        Assert.isFalse(passwordVo.getNewPassword().equals(passwordVo.getConfirmPassword()), new PasswordMismatchException("两次输入密码不一致"));

        user.setPassword(SecurityUtils.encryptPassword(passwordVo.getNewPassword()));
        return userMapper.updateUserByUserId(user);
    }

    /**
     * 修改用户头像
     *
     * @param userId     用户主键
     * @param avatarPath 头像路径
     * @return 影响行数
     */
    @Override
    public int updateAvatarByUserId(Long userId, String avatarPath) {
        return userMapper.updateAvatarByUserId(userId, avatarPath);
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

    /**
     * 批量删除用户
     *
     * @param userIds 用户主键数组
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByUserIds(Long[] userIds) {
        roleMapper.deleteUserRoleByUserIds(userIds);
        return userMapper.deleteUserByUserIds(userIds);
    }

}
