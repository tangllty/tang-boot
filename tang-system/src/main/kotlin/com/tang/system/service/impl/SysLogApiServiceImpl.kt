package com.tang.system.service.impl

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Objects

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import com.tang.commons.model.UserModel
import com.tang.system.entity.SysLogApi
import com.tang.system.mapper.SysLogApiMapper
import com.tang.system.service.SysLogApiService

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
     * 新增接口日志信息
     *
     * @param proceedingJoinPoint 切点
     * @param requestURI 请求地址
     * @param method 请求方式
     * @param userModel 用户信息
     * @param response 响应结果
     * @param startTimestamp 开始时间
     * @param endTimestamp 结束时间
     * @param throwable 异常信息
     * @param message 消息
     */
    @Async
    override fun insertSysLogApi(proceedingJoinPoint: ProceedingJoinPoint, requestURI: String?, method: String?,
        userModel: UserModel?, response: Any?, startTimestamp: LocalDateTime, endTimestamp: LocalDateTime,
        throwable: Throwable?, message: String) {
        val signature = proceedingJoinPoint.signature
        val className = proceedingJoinPoint.target.javaClass.name
        val methodName = signature.name
        val requestParams = proceedingJoinPoint.args.toList().toString()
        val between = endTimestamp.toInstant(ZoneOffset.UTC).toEpochMilli() - startTimestamp.toInstant(ZoneOffset.UTC).toEpochMilli()

        val sysLogApi = SysLogApi()
        sysLogApi.userId = userModel?.user?.userId
        sysLogApi.className = className
        sysLogApi.methodName = methodName
        sysLogApi.requestUri = requestURI
        sysLogApi.requestType = method
        sysLogApi.requestParam = requestParams
        sysLogApi.responseBody = response?.toString()
        sysLogApi.loginType = userModel?.loginType
        sysLogApi.ip = userModel?.ip
        sysLogApi.location = userModel?.location
        sysLogApi.startTime = startTimestamp
        sysLogApi.endTime = endTimestamp
        sysLogApi.costTime = between
        sysLogApi.statusCode = if (Objects.isNull(throwable)) "200" else "500"
        sysLogApi.message = message
        sysLogApiMapper.insertSysLogApi(sysLogApi)
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
