package com.tang.commons.utils;

import java.util.HashMap;

import com.tang.commons.constants.HttpStatus;

/**
 * Spring MVC 返回结果
 *
 * @author Tang
 */
public class AjaxResult extends HashMap<String, Object> {

    @java.io.Serial
    private static final long serialVersionUID = 9041781511255940851L;

    /**
     * 状态码
     */
    private static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    private static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    private static final String DATA_TAG = "data";

    /**
     * 操作成功信息
     */
    private static final String SUCCESS_MSG = "操作成功";

    /**
     * 操作失败信息
     */
    private static final String ERROR_MSG = "操作失败";

    /**
     * 初始化一个新创建的 {@link AjaxResult} 对象
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 {@link AjaxResult} 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 {@link AjaxResult} 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success(SUCCESS_MSG);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功数据
     *
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success(SUCCESS_MSG, data);
    }

    /**
     * 返回成功数据
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 失败消息
     */
    public static AjaxResult error() {
        return AjaxResult.error(ERROR_MSG);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 失败消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误数据
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 失败消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 失败消息
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    /**
     * 返回影响行数操作消息
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    public static AjaxResult rows(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 返回 boolean 操作消息
     *
     * @param flag 操作标识
     * @return 操作结果
     */
    public static AjaxResult rows(boolean flag) {
        return flag ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
