package com.tang.commons.enumeration;

/**
 * 消息类型枚举类
 *
 * @author Tang
 */
public enum MessageType {

    NOTICE("notice"),
    MESSAGE("message");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MessageType getMessageType(String name) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.getName().equals(name)) {
                return messageType;
            }
        }
        return null;
    }

}
