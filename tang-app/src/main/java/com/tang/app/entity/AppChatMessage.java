package com.tang.app.entity;

import com.tang.commons.base.entity.BaseEntity;

/**
 * 聊天消息实体类 app_chat_message
 *
 * @author Tang
 */
public class AppChatMessage extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = -1119467346890360549L;

    /**
     * 消息ID
     */
    private Long messageId;

    /**
     * 聊天列表ID
     */
    private Long chatListId;

    /**
     * 发送者ID
     */
    private Long senderId;

    private String username;

    private String nickname;

    private String avatar;

    /**
     * 聊天内容
     */
    private String content;


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getChatListId() {
        return chatListId;
    }

    public void setChatListId(Long chatListId) {
        this.chatListId = chatListId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
