package com.tang.commons.domain.dto;

import com.alibaba.fastjson2.JSON;
import com.tang.commons.enumeration.MessageType;

/**
 * 消息类
 *
 * @author Tang
 */
public class Message {

    private MessageType messageType;

    private String data;

    public Message() {
    }

    public Message(MessageType messageType, String data) {
        this.messageType = messageType;
        this.data = data;
    }

    public Message(String messageJsonString) {
        var message = JSON.parseObject(messageJsonString);
        this.messageType = MessageType.getMessageType(message.getString("messageType"));
        this.data = message.getString("data");
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

}
