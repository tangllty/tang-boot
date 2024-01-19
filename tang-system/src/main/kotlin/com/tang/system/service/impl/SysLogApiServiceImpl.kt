package com.tang.system.service.impl;

import org.springframework.stereotype.Service;

import com.tang.system.entity.SysLogApi;
import com.tang.system.mapper.SysLogApiMapper;
import com.tang.system.service.SysLogApiService;

/**
 * 接口日志业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
open class SysLogApiServiceImpl(private val sysLogApiMapper: SysLogApiMapper): SysLogApiService {

    /**
     * 查询接口日志列表
     *
     * @param sysLogApi 接口日志对象
     * @return 接口日志列表
     */
    override fun selectSysLogApiList(sysLogApi: SysLogApi): List<SysLogApi> {
        return sysLogApiMapper.selectSysLogApiList(sysLogApi)
    }

    /**
     * 通过接口日志主键查询接口日志信息
     *
     * @param apiId 接口日志主键
     * @return 接口日志信息
     */
    override fun selectSysLogApiByApiId(apiId: Long): SysLogApi {
        return sysLogApiMapper.selectSysLogApiByApiId(apiId)
    }

    /**
     * 新增接口日志信息
     *
     * @param sysLogApi 接口日志信息
     * @return 影响行数
     */
    override fun insertSysLogApi(sysLogApi: SysLogApi): Int {
        return sysLogApiMapper.insertSysLogApi(sysLogApi)
    }

    /**
     * 通过接口日志主键修改接口日志信息
     *
     * @param sysLogApi 接口日志信息
     * @return 影响行数
     */
    override fun updateSysLogApiByApiId(sysLogApi: SysLogApi): Int {
        return sysLogApiMapper.updateSysLogApiByApiId(sysLogApi)
    }

    /**
     * 通过接口日志主键删除接口日志信息
     *
     * @param apiId 接口日志主键
     * @return 影响行数
     */
    override fun deleteSysLogApiByApiId(apiId: Long): Int {
        return sysLogApiMapper.deleteSysLogApiByApiId(apiId)
    }

    /**
     * 通过接口日志主键数组批量删除接口日志信息
     *
     * @param apiIds 接口日志主键数组
     * @return 影响行数
     */
    override fun deleteSysLogApiByApiIds(apiIds: Array<Long>): Int {
        return sysLogApiMapper.deleteSysLogApiByApiIds(apiIds)
    }

}
