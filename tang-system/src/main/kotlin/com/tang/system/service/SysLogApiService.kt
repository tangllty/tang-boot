package com.tang.system.service;

import java.time.LocalDateTime

import org.aspectj.lang.ProceedingJoinPoint

import com.tang.system.entity.SysLogApi;

/**
 * 接口日志业务逻辑层接口
 *
 * @author Tang
 */
interface SysLogApiService {

    /**
     * 查询接口日志列表
     *
     * @param sysLogApi 接口日志对象
     * @return 接口日志列表
     */
    fun selectSysLogApiList(sysLogApi: SysLogApi): List<SysLogApi>

    /**
     * 通过接口日志主键查询接口日志信息
     *
     * @param apiId 接口日志主键
     * @return 接口日志信息
     */
    fun selectSysLogApiByApiId(apiId: Long): SysLogApi

    /**
     * 新增接口日志信息
     *
     * @param sysLogApi 接口日志信息
     * @return 影响行数
     */
    fun insertSysLogApi(sysLogApi: SysLogApi): Int

    /**
     * 新增接口日志信息
     *
     * @param proceedingJoinPoint 切点
     * @param requestURI 请求地址
     * @param method 请求方式
     * @param response 响应结果
     * @param startTimestamp 开始时间
     * @param endTimestamp 结束时间
     * @param throwable 异常信息
     * @param message 消息
     */
    fun insertSysLogApi(proceedingJoinPoint: ProceedingJoinPoint, requestURI: String?, method: String?, response: Any?,
        startTimestamp: LocalDateTime, endTimestamp: LocalDateTime, throwable: Throwable?, message: String)

    /**
     * 通过接口日志主键修改接口日志信息
     *
     * @param sysLogApi 接口日志信息
     * @return 影响行数
     */
    fun updateSysLogApiByApiId(sysLogApi: SysLogApi): Int

    /**
     * 通过接口日志主键删除接口日志信息
     *
     * @param apiId 接口日志主键
     * @return 影响行数
     */
    fun deleteSysLogApiByApiId(apiId: Long): Int

    /**
     * 通过接口日志主键数组批量删除接口日志信息
     *
     * @param apiIds 接口日志主键数组
     * @return 影响行数
     */
    fun deleteSysLogApiByApiIds(apiIds: Array<Long>): Int

}
