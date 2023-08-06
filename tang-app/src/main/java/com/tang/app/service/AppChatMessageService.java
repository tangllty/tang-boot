package com.tang.app.service;

import java.util.List;

import com.tang.app.entity.AppChatMessage;

/**
 * 聊天消息业务逻辑层接口
 *
 * @author Tang
 */
public interface AppChatMessageService {

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
