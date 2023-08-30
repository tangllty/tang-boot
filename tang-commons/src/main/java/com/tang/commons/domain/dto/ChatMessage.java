package com.tang.commons.domain.dto;

import com.alibaba.fastjson2.JSON;

/**
 * 聊天消息类
 *
 * @author Tang
 */
public class ChatMessage {

    private Long chatListId;

    private Long userId;

    private Long senderId;

    private String avatar;

    private String content;

    public ChatMessage(String data) {
        var jsonObject = JSON.parseObject(data);
        this.chatListId = jsonObject.getLong("chatListId");
        this.userId = jsonObject.getLong("userId");
        this.senderId = jsonObject.getLong("senderId");
        this.avatar = jsonObject.getString("avatar");
        this.content = jsonObject.getString("content");
    }

    public Long getChatListId() {
        return chatListId;
    }

    public void setChatListId(Long chatListId) {
        this.chatListId = chatListId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
