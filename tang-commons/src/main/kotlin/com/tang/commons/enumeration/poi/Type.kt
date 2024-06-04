package com.tang.commons.enumeration.poi

/**
 * Excel 字段类型
 *
 * @author Tang
 */
enum class Type(val code: Int) {

    /**
     * 导入导出
     */
    ALL(0),

    /**
     * 导入
     */
    IMPORT(1),

    /**
     * 导出
     */
    EXPORT(2);

    companion object {

        /**
         * 根据 code 返回枚举类型
         *
         * @param code 导出或导入
         * @return [Type] 导出或导入
         */
        fun getType(code: Int): Type? {
            for (type in Type.entries) {
                if (type.code == code) {
                    return type
                }
            }
            return null
        }

    }

}
