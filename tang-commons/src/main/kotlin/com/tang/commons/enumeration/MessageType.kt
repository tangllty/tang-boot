package com.tang.commons.enumeration

import com.fasterxml.jackson.annotation.JsonValue

/**
 * 消息类型枚举类
 *
 * @author Tang
 */
enum class MessageType(@JsonValue val value: String) {

    NOTICE("notice"),

    MESSAGE("message"),

    HEARTBEAT("heartbeat"),

    CHAT_MESSAGE("chat_message");

    companion object {

        /**
         * 根据 value 返回枚举类型
         *
         * @param value 消息类型
         * @return {@link MessageType} 消息类型名称
         */
        @JvmStatic
        fun getMessageType(value: String): MessageType? {
            return entries.find { it.value == value }
        }

    }

}
