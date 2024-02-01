package com.tang.app.service

import com.tang.app.entity.AppChatList

/**
 * 聊天列表业务逻辑层接口
 *
 * @author Tang
 */
interface AppChatListService {

    /**
     * 查询聊天列表列表
     *
     * @param appChatList 聊天列表对象
     * @return 聊天列表列表
     */
    fun selectAppChatListList(appChatList: AppChatList): List<AppChatList>

    /**
     * 查询当前用户聊天列表列表
     *
     * @return 聊天列表列表
     */
    fun selectAppChatListListAll(): List<AppChatList>

    /**
     * 通过聊天列表主键查询聊天列表信息
     *
     * @param chatListId 聊天列表主键
     * @return 聊天列表信息
     */
    fun selectAppChatListByChatListId(chatListId: Long): AppChatList?

    /**
     * 新增聊天列表信息
     *
     * @param appChatList 聊天列表信息
     * @return 影响行数
     */
    fun insertAppChatList(appChatList: AppChatList): Int

    /**
     * 通过聊天列表主键修改聊天列表信息
     *
     * @param appChatList 聊天列表信息
     * @return 影响行数
     */
    fun updateAppChatListByChatListId(appChatList: AppChatList): Int

    /**
     * 置顶聊天列表
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    fun stickByChatListId(chatListId: Long): Int

    /**
     * 取消置顶聊天列表
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    fun unstickByChatListId(chatListId: Long): Int

    /**
     * 通过聊天列表主键删除聊天列表信息
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    fun deleteAppChatListByChatListId(chatListId: Long): Int

    /**
     * 通过聊天列表主键数组批量删除聊天列表信息
     *
     * @param chatListIds 聊天列表主键数组
     * @return 影响行数
     */
    fun deleteAppChatListByChatListIds(chatListIds: Array<Long>): Int

}
