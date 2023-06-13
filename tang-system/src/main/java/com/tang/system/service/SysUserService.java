package com.tang.system.service;

import java.util.List;

import com.tang.commons.core.vo.PasswordVo;
import com.tang.system.entity.SysUser;

/**
 * 用户表 SysUser 表服务接口
 *
 * @author Tang
 */
public interface SysUserService {

    /**
     * 获取用户列表
     *
     * @param user 用户对象
     * @return 用户列表
     */
    List<SysUser> selectUserList(SysUser user);

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 用户对象
     */
    SysUser selectUserByUserId(Long userId);

    /**
     * 根据用户名查询单条数据
     *
     * @param username 用户名
     * @return 用户对象
     */
    SysUser selectUserByUsername(String username);

    /**
     * 根据邮箱查询单条数据
     *
     * @param email 邮箱
     * @return 用户对象
     */
    SysUser selectUserByEmail(String email);

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 影响行数
     */
    int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 影响行数
     */
    int updateUserByUserId(SysUser user);

    /**
     * 修改用户状态
     *
     * @param user 用户对象
     * @return 影响行数
     */
    int updateUserStatusByUserId(SysUser user);

    /**
     * 修改用户密码
     *
     * @param passwordVo 密码对象
     * @return 影响行数
     */
    int updatePasswordByUserId(PasswordVo passwordVo);

    /**
     * 修改用户头像
     *
     * @param userId     用户主键
     * @param avatarPath 头像路径
     * @return 影响行数
     */
    int updateAvatarByUserId(Long userId, String avatarPath);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteUserByUserId(Long userId);

    /**
     * 批量删除用户
     *
     * @param userIds 用户主键数组
     * @return 影响行数
     */
    int deleteUserByUserIds(Long[] userIds);

}
