package com.tang.commons.enumeration

/**
 * 文件大小单位枚举类
 *
 * @author Tang
 */
enum class SizeUnit(val value: Long) {

    B(1L),

    KB( B.value * 1024L),

    MB(KB.value * 1024L),

    GB(MB.value * 1024L),

    TB(GB.value * 1024L),

    PB(TB.value * 1024L),

    EB(PB.value * 1024L),

    ZB(EB.value * 1024L),

    YB(ZB.value * 1024L);

}
