package com.tang.app.entity

import com.tang.commons.base.entity.BaseEntity

/**
 * 聊天消息实体类 app_chat_message
 *
 * @author Tang
 */
class AppChatMessage : BaseEntity() {

    companion object {
        @JvmStatic
        @java.io.Serial
        var serialVersionUID = 1L
    }

    /**
     * 消息ID
     */
    var messageId: Long? = null

    /**
     * 聊天列表ID
     */
    var chatListId: Long? = null

    /**
     * 发送者ID
     */
    var senderId: Long? = null

    /**
     * 回复消息ID
     */
    var replyMessageId: Long? = null

    /**
     * 回复消息
     */
    var replyMessage: AppChatMessage? = null

    /**
     * 发送者用户名
     */
    var username: String? = null

    /**
     * 发送者昵称
     */
    var nickname: String? = null

    /**
     * 发送者头像
     */
    var avatar: String? = null

    /**
     * 聊天内容
     */
    var content: String? = null

}
