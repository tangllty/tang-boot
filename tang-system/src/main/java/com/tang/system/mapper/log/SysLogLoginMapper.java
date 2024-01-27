package com.tang.system.mapper.log;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tang.system.entity.log.SysLogLogin;

/**
 * 登陆日志数据访问层
 *
 * @author Tang
 */
public interface SysLogLoginMapper {

    /**
     * 查询登陆日志列表
     *
     * @param sysLogLogin 登陆日志对象
     * @return 登陆日志列表
     */
    List<SysLogLogin> selectSysLogLoginList(SysLogLogin sysLogLogin);

    /**
     * 查询用户登陆日志列表
     *
     * @param logLogin 登陆日志对象
     * @return 登陆日志列表
     */
    List<SysLogLogin> selectSysLogLoginListByUser(SysLogLogin logLogin);

    /**
     * 通过登陆日志主键查询登陆日志信息
     *
     * @param loginId 登陆日志主键
     * @return 登陆日志信息
     */
    SysLogLogin selectSysLogLoginByLoginId(Long loginId);

    /*
     * 查询用户访问量
     *
     * @startTime 开始时间
     * @return 用户访问量
     */
    List<SysLogLogin> selectUserVisit(@Param("startDate") LocalDate startDate);

    /**
     * 新增登陆日志信息
     *
     * @param sysLogLogin 登陆日志信息
     * @return 影响行数
     */
    int insertSysLogLogin(SysLogLogin sysLogLogin);

    /**
     * 通过登陆日志主键修改登陆日志信息
     *
     * @param sysLogLogin 登陆日志信息
     * @return 影响行数
     */
    int updateSysLogLoginByLoginId(SysLogLogin sysLogLogin);

    /**
     * 通过登陆日志主键删除登陆日志信息
     *
     * @param loginId 登陆日志主键
     * @return 影响行数
     */
    int deleteSysLogLoginByLoginId(Long loginId);

    /**
     * 通过登陆日志主键数组批量删除登陆日志信息
     *
     * @param loginIds 登陆日志主键数组
     * @return 影响行数
     */
    int deleteSysLogLoginByLoginIds(Long[] loginIds);

}
