package com.tang.generator.enumeration

/**
 * ORM 类型
 *
 * @author Tang
 */
enum class OrmType(val value: String) {

    MYBATIS("MyBatis"),

    MYBATIS_FLEX("MyBatis-Flex"),

    MYBATIS_PLUS("MyBatis-Plus");

    companion object {
        @JvmStatic
        fun getOrmType(value: String): OrmType? {
            return entries.find { it.value == value }
        }
    }

}
