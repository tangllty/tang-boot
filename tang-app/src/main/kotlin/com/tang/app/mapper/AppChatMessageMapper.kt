package com.tang.app.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

import com.tang.app.entity.AppChatMessage

/**
 * 用户好友申请数据访问层
 *
 * @author Tang
 */
@Mapper
interface AppChatMessageMapper {

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
     * 查询最后一条聊天消息信息
     *
     * @param chatListId 聊天列表主键
     * @param senderId 发送者主键
     * @return 最后一条聊天消息信息
     */
    fun selectLastMessage(@Param("chatListId") chatListId: Long, @Param("senderId") senderId: Long): AppChatMessage?

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
