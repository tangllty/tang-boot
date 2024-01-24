package com.tang.system.domain.vo

/**
 * 分析接口日志实体类 sys_log_api
 *
 * @author Tang
 */
class SysLogApiAnalysis {

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
     * 请求次数
     */
    var count: Long? = null

    /**
     * 总耗时
     */
    var totalTime: Long? = null

    /**
     * 平均耗时
     */
    var averageTime: Long? = null

    /**
     * 最小耗时
     */
    var minTime: Long? = null

    /**
     * 最大耗时
     */
    var maxTime: Long? = null

}
