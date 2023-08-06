package com.tang.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tang.system.entity.SysUser;

/**
 * 用户表 sys_user 表数据库访问层
 *
 * @author Tang
 */
public interface SysUserMapper {

    /**
     * 获取用户列表
     *
     * @param user 用户对象
     * @return 用户列表
     */
    List<SysUser> selectUserList(SysUser user);

    /**
     * 模糊查询用户列表
     *
     * @param userId  用户ID
     * @param keyword 关键字
     * @return 用户列表
     */
    List<SysUser> selectUserListFuzzy(@Param("userId") Long userId, @Param("keyword") String keyword);

    /**
     * 根据主键查询单条数据
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
     * @param user 用户对象
     * @return 影响行数
     */
    int updatePasswordByUserId(SysUser  user);

    /**
     * 修改用户头像
     *
     * @param userId     用户主键
     * @param avatarPath 头像路径
     * @return 影响行数
     */
    int updateAvatarByUserId(@Param("userId") Long userId, @Param("avatarPath") String avatarPath);

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
