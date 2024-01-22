package com.tang.framework.aop

import java.time.LocalDateTime
import java.time.ZoneOffset

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

import com.tang.commons.utils.AjaxResult
import com.tang.commons.utils.LogUtils
import com.tang.commons.utils.SecurityUtils
import com.tang.commons.utils.ServletUtils
import com.tang.commons.utils.SpringUtils
import com.tang.commons.utils.page.TableDataResult
import com.tang.commons.utils.time.TimeUtils
import com.tang.system.entity.SysLogApi
import com.tang.system.mapper.SysLogApiMapper

/**
 * 控制器切面类，记录接口调用日志
 *
 * @author Tang
 */
@Aspect
@Component
class ControllerAspect {

    companion object {
        private val sysLogApiMapper = SpringUtils.getBean(SysLogApiMapper::class.java)
    }

    @Around("execution(* com.tang..controller..*.*(..))")
    fun around(proceedingJoinPoint: ProceedingJoinPoint): Any {
        val signature = proceedingJoinPoint.signature
        val className = proceedingJoinPoint.target.javaClass.name
        val methodName = signature.name
        val requestParams = proceedingJoinPoint.args.toList().toString()
        val startTimestamp = LocalDateTime.now()
        var message = ""
        var throwable: Throwable? = null
        var response: Any? = null
        message = try {
            response = proceedingJoinPoint.proceed()
            when (response) {
                is AjaxResult -> response["msg"].toString()
                is TableDataResult -> response.msg.toString()
                else -> "成功"
            }
        } catch (e: Exception) {
            LogUtils.error("请求失败", e)
            throwable = e
            e.message.toString()
        }
        val endTimestamp = LocalDateTime.now()
        val between = endTimestamp.toInstant(ZoneOffset.UTC).toEpochMilli() - startTimestamp.toInstant(ZoneOffset.UTC).toEpochMilli()

        val sysLogApi = SysLogApi()
        sysLogApi.userId = if (SecurityUtils.isAuthenticated()) SecurityUtils.getUser().userId else null
        sysLogApi.className = className
        sysLogApi.methodName = methodName
        sysLogApi.requestUri = ServletUtils.getRequest().requestURI
        sysLogApi.requestType = ServletUtils.getRequest().method
        sysLogApi.requestParam = requestParams
        sysLogApi.responseBody = response?.toString()
         sysLogApi.loginType = if (SecurityUtils.isAuthenticated()) SecurityUtils.getUserModel().loginType else null
        sysLogApi.ip = if (SecurityUtils.isAuthenticated()) SecurityUtils.getUserModel().ip else null
        sysLogApi.location = if (SecurityUtils.isAuthenticated()) SecurityUtils.getUserModel().location else null
        sysLogApi.startTime = startTimestamp
        sysLogApi.endTime = endTimestamp
        sysLogApi.costTime = between
        sysLogApi.statusCode = if (throwable == null) "200" else "500"
        sysLogApi.message = message
        sysLogApiMapper.insertSysLogApi(sysLogApi)

        if (throwable != null) {
            throw throwable
        }

        return response ?: Any()
    }

}
