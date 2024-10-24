package com.tang.commons.annotation.poi

import org.springframework.core.annotation.AliasFor

import com.tang.commons.enumeration.poi.CellType
import com.tang.commons.enumeration.poi.Type

/**
 * Excel 注解
 *
 * @author Tang
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Excel(

    /**
     * Alias for [name]
     */
    @get:AliasFor("name") val value: String = "",

    /**
     * 列名称
     */
    @get:AliasFor("value") val name: String = "",

    /**
     * 顺序
     */
    val sort: Int = 0,

    /**
     * 单元格类型
     */
    val cellType: CellType = CellType.STRING,

    /**
     * 日期格式
     */
    val dateFormat: String = "yyyy-MM-dd HH:mm:ss",

    /**
     * 字段类型
     */
    val type: Type = Type.ALL,

    /**
     * 字典类型
     */
    val dictType: String = "",

    /**
     * 列宽，-1为自适应
     *
     * @see com.tang.commons.utils.poi.Excels.setTitle
     */
    val width: Int = -1,

)
