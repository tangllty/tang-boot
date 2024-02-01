package com.tang.app.service.impl

import java.util.Objects
import java.util.function.Consumer

import org.springframework.stereotype.Service

import com.tang.app.constants.AppChatListStick
import com.tang.app.entity.AppChatList
import com.tang.app.mapper.AppChatListMapper
import com.tang.app.mapper.AppChatMessageMapper
import com.tang.app.service.AppChatListService
import com.tang.commons.utils.SecurityUtils
import com.tang.commons.utils.time.DateUtils
import com.tang.system.mapper.SysUserMapper

/**
 * 聊天列业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
class AppChatListServiceImpl(
    private val appChatListMapper: AppChatListMapper,
    private val appChatMessageMapper: AppChatMessageMapper,
    private val userMapper: SysUserMapper
) : AppChatListService {

    /**
     * 查询聊天列列表
     *
     * @param appChatList 聊天列对象
     * @return 聊天列列表
     */
    override fun selectAppChatListList(appChatList: AppChatList): List<AppChatList> {
        return appChatListMapper.selectAppChatListList(appChatList)
    }

    /**
     * 查询当前用户聊天列表列表
     *
     * @return 聊天列表列表
     */
    override fun selectAppChatListListAll(): List<AppChatList> {
        val userId = SecurityUtils.getUser().userId
        val list: List<AppChatList> = appChatListMapper.selectAppChatListListAllByUserId(userId)
        list.forEach(Consumer { item: AppChatList ->
            val lastMessage = appChatMessageMapper.selectLastMessage(item.chatListId, item.chatId)
            if (Objects.nonNull(lastMessage)) {
                item.avatar = userMapper.selectUserByUserId(item.chatId).avatar
                item.message = lastMessage.content
                item.time = DateUtils.getChatTime(lastMessage.createTime)
            }
        })
        return list
    }

    /**
     * 通过聊天列主键查询聊天列信息
     *
     * @param chatListId 聊天列主键
     * @return 聊天列信息
     */
    override fun selectAppChatListByChatListId(chatListId: Long): AppChatList {
        return appChatListMapper.selectAppChatListByChatListId(chatListId)
    }

    /**
     * 新增聊天列信息
     *
     * @param appChatList 聊天列信息
     * @return 影响行数
     */
    override fun insertAppChatList(appChatList: AppChatList): Int {
        return appChatListMapper.insertAppChatList(appChatList)
    }

    /**
     * 通过聊天列主键修改聊天列信息
     *
     * @param appChatList 聊天列信息
     * @return 影响行数
     */
    override fun updateAppChatListByChatListId(appChatList: AppChatList): Int {
        return appChatListMapper.updateAppChatListByChatListId(appChatList)
    }

    /**
     * 置顶聊天列表
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    override fun stickByChatListId(chatListId: Long): Int {
        val appChatList = AppChatList()
        appChatList.chatListId = chatListId
        appChatList.stickFlag = AppChatListStick.STICK
        return appChatListMapper.updateAppChatListByChatListId(appChatList)
    }

    /**
     * 取消置顶聊天列表
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    override fun unstickByChatListId(chatListId: Long): Int {
        val appChatList = AppChatList()
        appChatList.chatListId = chatListId
        appChatList.stickFlag = AppChatListStick.UNSTICK
        return appChatListMapper.updateAppChatListByChatListId(appChatList)
    }

    /**
     * 通过聊天列主键删除聊天列信息
     *
     * @param chatListId 聊天列主键
     * @return 影响行数
     */
    override fun deleteAppChatListByChatListId(chatListId: Long): Int {
        return appChatListMapper.deleteAppChatListByChatListId(chatListId)
    }

    /**
     * 通过聊天列主键数组批量删除聊天列信息
     *
     * @param chatListIds 聊天列主键数组
     * @return 影响行数
     */
    override fun deleteAppChatListByChatListIds(chatListIds: Array<Long>): Int {
        return appChatListMapper.deleteAppChatListByChatListIds(chatListIds)
    }

}
