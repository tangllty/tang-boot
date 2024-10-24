package com.tang.commons.annotation.poi

/**
 * Excel 配置注解
 *
 * @author Tang
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ExcelConfig(

    val mainTitle: String = "",

    val mainTitleHeight: Int = 24,

    val titleHeight: Int = 16,

    val dataHeight: Int = 16

)
