package com.tang.commons.constants

/**
 * 返回状态码
 *
 * @author Tang
 */
object HttpStatus {

    /**
     * 操作成功
     */
    const val SUCCESS = 200

    /**
     * 对象创建成功
     */
    const val CREATED = 201

    /**
     * 请求已经被接受
     */
    const val ACCEPTED = 202

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    const val NO_CONTENT = 204

    /**
     * 资源已被移除
     */
    const val MOVED_PERM = 301

    /**
     * 重定向
     */
    const val SEE_OTHER = 303

    /**
     * 资源没有被修改
     */
    const val NOT_MODIFIED = 304

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    const val BAD_REQUEST = 400

    /**
     * 未授权
     */
    const val UNAUTHORIZED = 401

    /**
     * 访问受限，授权过期
     */
    const val FORBIDDEN = 403

    /**
     * 资源，服务未找到
     */
    const val NOT_FOUND = 404

    /**
     * 不允许的http方法
     */
    const val BAD_METHOD = 405

    /**
     * 资源冲突，或者资源被锁
     */
    const val CONFLICT = 409

    /**
     * 不支持的数据，媒体类型
     */
    const val UNSUPPORTED_TYPE = 415

    /**
     * 系统内部错误
     */
    const val ERROR = 500

    /**
     * 接口未实现
     */
    const val NOT_IMPLEMENTED = 501

}
