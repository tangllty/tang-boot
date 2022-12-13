package com.tang.system.mapper;

import java.util.List;

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

