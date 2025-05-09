package com.tang.commons.enumeration

/**
 * 菜单类型
 *
 * @author Tang
 */
enum class MenuType(val value: String) {

    /**
     * 目录
     */
    DIRECTORY("D"),

    /**
     * 菜单
     */
    MENU("M"),

    /**
     * 按钮
     */
    BUTTON("B"),

    /**
     * 页面
     */
    PAGE("P");

    companion object {

        /**
         * 根据 value 返回枚举类型
         *
         * @param value 菜单类型
         * @return [MenuType] 菜单类型名称
         */
        @JvmStatic
        fun getMenuType(value: String): MenuType? {
            return entries.find { it.value == value }
        }

    }

}
