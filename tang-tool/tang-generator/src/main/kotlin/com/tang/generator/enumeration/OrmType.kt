package com.tang.generator.enumeration

/**
 * ORM 类型
 *
 * @author Tang
 */
enum class OrmType(val value: String) {

    MYBATIS("MyBatis"),

    MYBATIS_PLUS("MyBatis-Plus");

    companion object {
        fun getOrmType(value: String): OrmType? {
            return entries.find { it.value == value }
        }
    }

}
