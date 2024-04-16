package com.tang.commons.utils.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * JSON 工具类
 *
 * @author Tang
 */
object JSONUtils {

    private val objectMapper = ObjectMapper().findAndRegisterModules()

    @JvmStatic
    fun toStringPretty(obj: Any): String {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
    }

    @JvmStatic
    fun toString(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    @JvmStatic
    fun <T> parse(json: String, valueType: Class<T>): T {
        return objectMapper.readValue(json, valueType)
    }

    @JvmStatic
    fun <T> parse(json: String, valueTypeRef: TypeReference<T>): T {
        return objectMapper.readValue(json, valueTypeRef)
    }

}
