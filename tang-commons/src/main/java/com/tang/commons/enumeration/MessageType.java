package com.tang.commons.enumeration;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 消息类型枚举类
 *
 * @author Tang
 */
public enum MessageType {

    NOTICE("notice"),
    MESSAGE("message"),
    HEARTBEAT("heartbeat"),
    CHAT_MESSAGE("chat_message");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static MessageType getMessageType(String name) {
        return Arrays.stream(MessageType.values())
            .filter(messageType -> messageType.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

}
