package com.tang.system.entity

import java.time.LocalDateTime

import com.tang.commons.base.entity.BaseEntity

/**
 * 接口日志实体类 sys_log_api
 *
 * @author Tang
 */
class SysLogApi : BaseEntity() {

    companion object {
        @java.io.Serial
        private val serialVersionUID = 1L
    }

    /**
     * 日志ID
     */
    var apiId: Long? = null

    /**
     * 用户ID
     */
    var userId: Long? = null

    /**
     * 类名称
     */
    var className: String? = null

    /**
     * 方法名称
     */
    var methodName: String? = null

    /**
     * 请求URI
     */
    var requestUri: String? = null

    /**
     * 请求类型
     */
    var requestType: String? = null

    /**
     * 请求体
     */
    var requestParam: String? = null

    /**
     * 请求参数
     */
    var requestQuery: String? = null

    /**
     * 响应体
     */
    var responseBody: String? = null

    /**
     * 登陆类型
     */
    var loginType: String? = null

    /**
     * 登录IP地址
     */
    var ip: String? = null

    /**
     * 登录地点
     */
    var location: String? = null

    /**
     * 开始时间
     */
    var startTime: LocalDateTime? = null

    /**
     * 结束时间
     */
    var endTime: LocalDateTime? = null

    /**
     * 耗时
     */
    var costTime: Long? = null

    /**
     * 状态码
     */
    var statusCode: String? = null

    /**
     * 消息
     */
    var message: String? = null

}
