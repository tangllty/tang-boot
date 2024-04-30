package com.tang.framework.aop

import java.time.LocalDateTime

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

import com.tang.commons.utils.AjaxResult
import com.tang.commons.utils.LogUtils
import com.tang.commons.utils.SecurityUtils
import com.tang.commons.utils.ServletUtils
import com.tang.commons.utils.SpringUtils
import com.tang.commons.utils.page.PageResult
import com.tang.system.service.SysLogApiService

/**
 * 控制器切面类，记录接口调用日志
 *
 * @author Tang
 */
@Aspect
@Component
class ControllerAspect {

    companion object {
        private val sysLogApiService = SpringUtils.getBean(SysLogApiService::class.java)
    }

    @Around("execution(* com.tang..controller..*.*(..))")
    fun around(proceedingJoinPoint: ProceedingJoinPoint): Any {
        val startTimestamp = LocalDateTime.now()
        val message: String
        var throwable: Throwable? = null
        var response: Any? = null
        message = try {
            response = proceedingJoinPoint.proceed()
            when (response) {
                is AjaxResult -> response["msg"].toString()
                is PageResult -> response.msg.toString()
                else -> "成功"
            }
        } catch (e: Exception) {
            LogUtils.error("请求失败", e)
            throwable = e
            e.message.toString()
        }
        val endTimestamp = LocalDateTime.now()

        val requestURI = ServletUtils.getRequest().requestURI
        val method = ServletUtils.getRequest().method
        val query = ServletUtils.getRequest().queryString
        val userModel = if (SecurityUtils.isAuthenticated()) SecurityUtils.getUserModel() else null
        sysLogApiService.insertSysLogApi(proceedingJoinPoint, requestURI, method, query, userModel, response, startTimestamp, endTimestamp, throwable, message)

        if (throwable != null) {
            throw throwable
        }

        return response ?: Any()
    }

}
