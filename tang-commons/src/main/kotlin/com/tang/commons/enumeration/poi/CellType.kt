package com.tang.commons.enumeration.poi

/**
 * Excel 单元格类型
 *
 * @author Tang
 */
enum class CellType(val code: Int) {

    /**
     * 字符串
     */
    STRING(0),

    /**
     * 数字
     */
    NUMBER(1),

    /**
     * 日期
     */
    DATE(2);

    companion object {

        /**
         * 根据 code 返回枚举类型
         *
         * @param code 单元格类型
         * @return [CellType] 单元格类型
         */
        fun getCellType(code: Int): CellType? {
            for (cellType in CellType.entries) {
                if (cellType.code == code) {
                    return cellType
                }
            }
            return null
        }

    }

}
