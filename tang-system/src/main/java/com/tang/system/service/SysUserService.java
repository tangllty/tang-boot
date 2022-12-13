package com.tang.system.service;

import java.util.List;

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
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteUserByUserId(Long userId);

}
