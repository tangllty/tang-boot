package com.tang.app.service;

import java.util.List;

import com.tang.app.entity.AppChatList;

/**
 * 聊天列表业务逻辑层接口
 *
 * @author Tang
 */
public interface AppChatListService {

    /**
     * 查询聊天列表列表
     *
     * @param appChatList 聊天列表对象
     * @return 聊天列表列表
     */
    List<AppChatList> selectAppChatListList(AppChatList appChatList);

    /**
     * 查询当前用户聊天列表列表
     *
     * @return 聊天列表列表
     */
    List<AppChatList> selectAppChatListListAll();

    /**
     * 通过聊天列表主键查询聊天列表信息
     *
     * @param chatListId 聊天列表主键
     * @return 聊天列表信息
     */
    AppChatList selectAppChatListByChatListId(Long chatListId);

    /**
     * 新增聊天列表信息
     *
     * @param appChatList 聊天列表信息
     * @return 影响行数
     */
    int insertAppChatList(AppChatList appChatList);

    /**
     * 通过聊天列表主键修改聊天列表信息
     *
     * @param appChatList 聊天列表信息
     * @return 影响行数
     */
    int updateAppChatListByChatListId(AppChatList appChatList);

    /**
     * 通过聊天列表主键删除聊天列表信息
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    int deleteAppChatListByChatListId(Long chatListId);

    /**
     * 通过聊天列表主键数组批量删除聊天列表信息
     *
     * @param chatListIds 聊天列表主键数组
     * @return 影响行数
     */
    int deleteAppChatListByChatListIds(Long[] chatListIds);

}
