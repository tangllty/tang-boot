package com.tang.commons.enumeration

/**
 * 登陆方式
 *
 * @author Tang
 */
enum class LoginType(val value: String, val label: String) {

    /**
     * 用户名密码
     */
    USERNAME("username", "用户名密码"),

    /**
     * 邮箱密码
     */
    EMAIL("email", "邮箱密码"),

    /**
     * GitHub 授权码
     */
    GITHUB("github", "GitHub 授权码"),

    /**
     * 未知
     */
    UNKNOWN("", "未知");

    companion object {

        /**
         * 根据 value 返回枚举类型
         *
         * @param value 登陆方式
         * @return {@link LoginType} 登陆方式名称
         */
        @JvmStatic
        fun getLoginType(value: String): LoginType {
            return entries.find { it.value == value } ?: UNKNOWN
        }

    }

}
