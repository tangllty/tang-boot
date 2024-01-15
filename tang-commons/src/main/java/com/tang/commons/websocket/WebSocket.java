package com.tang.commons.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.tang.commons.domain.dto.Message;
import com.tang.commons.enumeration.MessageType;
import com.tang.commons.utils.LogUtils;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * WebSocket 服务器端点
 *
 * @author Tang
 */
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {

    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 会话池，用于存储用户 ID 与对应的会话对象
     */
    private static final Map<String, Session> SESSION_POOL = new ConcurrentHashMap<>(16);

    /**
     * 消息池，用于存储消息类型与对应的回调函数
     */
    private static final Map<String, List<Consumer<String>>> MESSAGES = new HashMap<>(16);

    /**
     * WebSocket 连接建立时触发
     *
     * @param userId 用户 ID
     * @param session 会话对象
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userId") String userId, Session session) {
        if (SESSION_POOL.containsKey(userId)) {
            LOGGER.error("WebSocket connection already exists for user: {}", userId);
            return;
        }
        SESSION_POOL.put(userId, session);
        LOGGER.info("WebSocket connection opened, total: {}", SESSION_POOL.size());
    }

    /**
     * WebSocket 接收到消息时触发
     *
     * @param session 会话对象
     * @param messageJsonString 消息 JSON 字符串
     */
    @OnMessage
    public void onMessage(Session session, String messageJsonString) {
        var message = new Message(messageJsonString);
        var messageType = message.getMessageType();
        LOGGER.info("WebSocket received message type: {}, data: {}", messageType, message.getData());

        if (MESSAGES.containsKey(messageType.getValue())) {
            MESSAGES.get(messageType.getValue()).forEach(callback -> callback.accept(message.getData()));
        }
    }

    /**
     * WebSocket 连接关闭时触发
     *
     * @param userId 用户 ID
     */
    @OnClose
    public void onClose(@PathParam(value = "userId") String userId) {
        try (Session closedSession = SESSION_POOL.remove(userId)) {
            LOGGER.info("WebSocket connection closed, total: {}", SESSION_POOL.size());
        } catch (IOException e) {
            LOGGER.error("WebSocket connection closed error: {}", e.getMessage());
        }
    }

    /**
     * WebSocket 发生错误时触发
     *
     * @param session 会话对象
     * @param error 错误对象
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.error("WebSocket error: {}", error.getMessage());
    }

    /**
     * 向指定用户发送消息
     *
     * @param userId 用户 ID
     * @param message 消息对象
     */
    public void sendMessage(String userId, Message message) {
        if (!SESSION_POOL.containsKey(userId) || !SESSION_POOL.get(userId).isOpen()) {
            LOGGER.error("WebSocket session is not open for user: {}", userId);
            return;
        }

        SESSION_POOL.get(userId).getAsyncRemote().sendText(message.toJsonString());
    }

    /**
     * 向指定用户发送消息
     *
     * @param userId 用户 ID
     * @param message 消息对象
     */
    public void sendMessage(Long userId, Message message) {
        sendMessage(String.valueOf(userId), message);
    }

    /**
     * 向指定用户发送消息
     *
     * @param userId 用户 ID
     * @param messageType 消息类型
     * @param message 消息内容
     */
    public void sendMessage(String userId, MessageType messageType, String message) {
        sendMessage(userId, new Message(messageType, message));
    }

    /**
     * 向所有用户发送消息
     *
     * @param message 消息对象
     */
    public void sendAllMessage(Message message) {
        SESSION_POOL.forEach((userId, session) -> {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message.toJsonString());
            }
        });
    }

    /**
     * 向所有用户发送消息
     *
     * @param messageType 消息类型
     * @param message 消息内容
     */
    public void sendAllMessage(MessageType messageType, String message) {
        sendAllMessage(new Message(messageType, message));
    }

    /**
     * 订阅特定消息类型
     *
     * @param messageType 消息类型
     * @param callback 回调函数
     */
    public void subscribe(MessageType messageType, Consumer<String> callback) {
        MESSAGES.computeIfAbsent(messageType.getValue(), k -> new ArrayList<>()).add(callback);
    }

    /**
     * 取消订阅特定消息类型
     *
     * @param messageType 消息类型
     * @param callback 回调函数
     */
    public void unsubscribe(MessageType messageType, Consumer<String> callback) {
        if (!MESSAGES.containsKey(messageType.getValue()) || !MESSAGES.get(messageType.getValue()).contains(callback)) {
            return;
        }

        MESSAGES.get(messageType.getValue()).remove(callback);
    }

}
