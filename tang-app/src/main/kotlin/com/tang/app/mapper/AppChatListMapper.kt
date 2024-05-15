package com.tang.app.mapper

import org.apache.ibatis.annotations.Mapper

import com.tang.app.entity.AppChatList

/**
 * 聊天列表数据访问层
 *
 * @author Tang
 */
@Mapper
interface AppChatListMapper {

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
     * @param userId 用户主键
     * @return 聊天列表列表
     */
    fun selectAppChatListListAllByUserId(userId: Long): List<AppChatList>

    /**
     * 通过聊天列表主键查询聊天列表信息
     *
     * @param chatListId 聊天列表主键
     * @return 聊天列表信息
     */
    fun selectAppChatListByChatListId(chatListId: Long): AppChatList

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
