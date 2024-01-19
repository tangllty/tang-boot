package com.tang.system.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tang.commons.base.entity.BaseEntity;

/**
 * 接口日志实体类 sys_log_api
 *
 * @author Tang
 */
class SysLogApi: BaseEntity() {

    companion object {
        @JvmStatic private val serialVersionUID = 1L
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
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var startTime: LocalDateTime? = null

    /**
     * 结束时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
