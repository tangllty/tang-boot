package com.tang.system.mapper

import com.tang.system.entity.SysLogApi

/**
 * 接口日志数据访问层
 *
 * @author Tang
 */
interface SysLogApiMapper {

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
