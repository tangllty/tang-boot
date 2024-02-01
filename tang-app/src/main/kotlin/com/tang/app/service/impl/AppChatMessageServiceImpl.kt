package com.tang.app.service.impl

import org.springframework.stereotype.Service

import com.tang.app.entity.AppChatMessage
import com.tang.app.mapper.AppChatMessageMapper
import com.tang.app.service.AppChatMessageService
import com.tang.commons.domain.dto.ChatMessage
import com.tang.commons.domain.dto.Message
import com.tang.commons.enumeration.MessageType
import com.tang.commons.websocket.WebSocket
import com.tang.system.service.SysUserService

import jakarta.annotation.PostConstruct

/**
 * 聊天消息业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
class AppChatMessageServiceImpl(
    private val appChatMessageMapper: AppChatMessageMapper,
    private val userService: SysUserService,
    private val webSocket: WebSocket
) : AppChatMessageService {

    @PostConstruct
    fun init() {
        webSocket.subscribe(MessageType.CHAT_MESSAGE) { data: String ->
            val chatMessage = ChatMessage(data)
            val message = Message(MessageType.CHAT_MESSAGE, data)
            webSocket.sendMessage(chatMessage.userId, message)
        }
    }

    /**
     * 查询聊天消息列表
     *
     * @param appChatMessage 聊天消息对象
     * @return 聊天消息列表
     */
    override fun selectAppChatMessageList(appChatMessage: AppChatMessage): List<AppChatMessage> {
        var list: List<AppChatMessage> = appChatMessageMapper.selectAppChatMessageList(appChatMessage)
        list = list.sortedBy { it.messageId }
        list.forEach {
            it.replyMessage = it.replyMessageId?.let {
                messageId -> appChatMessageMapper.selectAppChatMessageByMessageId(messageId)
            }
        }
        return list
    }

    /**
     * 通过聊天消息主键查询聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 聊天消息信息
     */
    override fun selectAppChatMessageByMessageId(messageId: Long): AppChatMessage? {
        return appChatMessageMapper.selectAppChatMessageByMessageId(messageId)
    }

    /**
     * 新增聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    override fun insertAppChatMessage(appChatMessage: AppChatMessage): Int {
        val rows = appChatMessageMapper.insertAppChatMessage(appChatMessage)
        if (rows <= 0) {
            return rows
        }
        appChatMessage.avatar = userService.selectUserByUserId(appChatMessage.senderId).avatar
        return rows
    }

    /**
     * 通过聊天消息主键修改聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    override fun updateAppChatMessageByMessageId(appChatMessage: AppChatMessage): Int {
        return appChatMessageMapper.updateAppChatMessageByMessageId(appChatMessage)
    }

    /**
     * 通过聊天消息主键删除聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 影响行数
     */
    override fun deleteAppChatMessageByMessageId(messageId: Long): Int {
        return appChatMessageMapper.deleteAppChatMessageByMessageId(messageId)
    }

    /**
     * 通过聊天消息主键数组批量删除聊天消息信息
     *
     * @param messageIds 聊天消息主键数组
     * @return 影响行数
     */
    override fun deleteAppChatMessageByMessageIds(messageIds: Array<Long>): Int {
        return appChatMessageMapper.deleteAppChatMessageByMessageIds(messageIds)
    }

}
