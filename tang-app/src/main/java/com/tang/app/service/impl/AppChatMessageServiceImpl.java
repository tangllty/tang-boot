package com.tang.app.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tang.app.entity.AppChatMessage;
import com.tang.app.mapper.AppChatMessageMapper;
import com.tang.app.service.AppChatMessageService;
import com.tang.commons.domain.dto.ChatMessage;
import com.tang.commons.domain.dto.Message;
import com.tang.commons.enumeration.MessageType;
import com.tang.commons.websocket.WebSocket;

import jakarta.annotation.PostConstruct;

/**
 * 聊天消息业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
public class AppChatMessageServiceImpl implements AppChatMessageService {

    private final AppChatMessageMapper appChatMessageMapper;

    private final WebSocket webSocket;

    public AppChatMessageServiceImpl(AppChatMessageMapper appChatMessageMapper, WebSocket webSocket) {
        this.appChatMessageMapper = appChatMessageMapper;
        this.webSocket = webSocket;
    }

    @PostConstruct
    public void init() {
        webSocket.subscribe(MessageType.CHAT_MESSAGE, data -> {
            var chatMessage = new ChatMessage(data);
            var message = new Message(MessageType.CHAT_MESSAGE, data);
            webSocket.sendMessage(chatMessage.getUserId(), message);
        });
    }

    /**
     * 查询聊天消息列表
     *
     * @param appChatMessage 聊天消息对象
     * @return 聊天消息列表
     */
    @Override
    public List<AppChatMessage> selectAppChatMessageList(AppChatMessage appChatMessage) {
        var list = appChatMessageMapper.selectAppChatMessageList(appChatMessage);
        list.sort(Comparator.comparing(AppChatMessage::getMessageId));
        return list;
    }

    /**
     * 通过聊天消息主键查询聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 聊天消息信息
     */
    @Override
    public AppChatMessage selectAppChatMessageByMessageId(Long messageId) {
        return appChatMessageMapper.selectAppChatMessageByMessageId(messageId);
    }

    /**
     * 新增聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    @Override
    public int insertAppChatMessage(AppChatMessage appChatMessage) {
        return appChatMessageMapper.insertAppChatMessage(appChatMessage);
    }

    /**
     * 通过聊天消息主键修改聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    @Override
    public int updateAppChatMessageByMessageId(AppChatMessage appChatMessage) {
        return appChatMessageMapper.updateAppChatMessageByMessageId(appChatMessage);
    }

    /**
     * 通过聊天消息主键删除聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 影响行数
     */
    @Override
    public int deleteAppChatMessageByMessageId(Long messageId) {
        return appChatMessageMapper.deleteAppChatMessageByMessageId(messageId);
    }

    /**
     * 通过聊天消息主键数组批量删除聊天消息信息
     *
     * @param messageIds 聊天消息主键数组
     * @return 影响行数
     */
    @Override
    public int deleteAppChatMessageByMessageIds(Long[] messageIds) {
        return appChatMessageMapper.deleteAppChatMessageByMessageIds(messageIds);
    }

}
