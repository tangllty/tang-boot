package com.tang.commons.enumeration

/**
 * 用户类型
 *
 * @author Tang
 */
enum class UserType(val value: String, val label: String) {

    /**
     * 系统用户
     */
    SYSTEM("0", "系统用户"),

    /**
     * Github 用户
     */
    GITHUB("1", "Github 用户");

    companion object {

        /**
         * 根据 value 返回枚举类型
         *
         * @param value 用户类型
         * @return {@link UserType} 用户类型名称
         */
        @JvmStatic
        fun getUserType(value: String): UserType {
            return entries.find { it.value == value } ?: SYSTEM
        }

    }

}
