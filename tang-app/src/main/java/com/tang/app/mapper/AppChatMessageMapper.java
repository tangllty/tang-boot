package com.tang.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tang.app.entity.AppChatMessage;

/**
 * 聊天消息数据访问层
 *
 * @author Tang
 */
public interface AppChatMessageMapper {

    /**
     * 查询聊天消息列表
     *
     * @param appChatMessage 聊天消息对象
     * @return 聊天消息列表
     */
    List<AppChatMessage> selectAppChatMessageList(AppChatMessage appChatMessage);

    /**
     * 通过聊天消息主键查询聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 聊天消息信息
     */
    AppChatMessage selectAppChatMessageByMessageId(Long messageId);

    /**
     * 查询最后一条聊天消息信息
     *
     * @param chatListId 聊天列表主键
     * @param senderId 发送者主键
     * @return 最后一条聊天消息信息
     */
    AppChatMessage selectLastMessage(@Param("chatListId") Long chatListId, @Param("senderId") Long senderId);

    /**
     * 新增聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    int insertAppChatMessage(AppChatMessage appChatMessage);

    /**
     * 通过聊天消息主键修改聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    int updateAppChatMessageByMessageId(AppChatMessage appChatMessage);

    /**
     * 通过聊天消息主键删除聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 影响行数
     */
    int deleteAppChatMessageByMessageId(Long messageId);

    /**
     * 通过聊天消息主键数组批量删除聊天消息信息
     *
     * @param messageIds 聊天消息主键数组
     * @return 影响行数
     */
    int deleteAppChatMessageByMessageIds(Long[] messageIds);

}
