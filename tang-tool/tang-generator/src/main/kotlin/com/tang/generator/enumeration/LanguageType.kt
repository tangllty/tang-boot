package com.tang.generator.enumeration

/**
 * 语言类型
 *
 * @author Tang
 */
enum class LanguageType(val value: String) {

    JAVA("Java"),

    KOTLIN("Kotlin");

    companion object {
        @JvmStatic
        fun getLanguageType(value: String): LanguageType? {
            return entries.find { it.value == value }
        }
    }

}
