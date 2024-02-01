package com.tang.app.service

import com.tang.app.entity.AppChatMessage

/**
 * 聊天消息业务逻辑层接口
 *
 * @author Tang
 */
interface AppChatMessageService {

    /**
     * 查询聊天消息列表
     *
     * @param appChatMessage 聊天消息对象
     * @return 聊天消息列表
     */
    fun selectAppChatMessageList(appChatMessage: AppChatMessage): List<AppChatMessage>

    /**
     * 通过聊天消息主键查询聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 聊天消息信息
     */
    fun selectAppChatMessageByMessageId(messageId: Long): AppChatMessage?

    /**
     * 新增聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    fun insertAppChatMessage(appChatMessage: AppChatMessage): Int

    /**
     * 通过聊天消息主键修改聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    fun updateAppChatMessageByMessageId(appChatMessage: AppChatMessage): Int

    /**
     * 通过聊天消息主键删除聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 影响行数
     */
    fun deleteAppChatMessageByMessageId(messageId: Long): Int

    /**
     * 通过聊天消息主键数组批量删除聊天消息信息
     *
     * @param messageIds 聊天消息主键数组
     * @return 影响行数
     */
    fun deleteAppChatMessageByMessageIds(messageIds: Array<Long>): Int

}
