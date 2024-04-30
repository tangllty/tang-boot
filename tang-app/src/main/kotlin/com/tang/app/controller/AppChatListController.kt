package com.tang.app.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.tang.app.entity.AppChatList
import com.tang.app.service.AppChatListService
import com.tang.commons.utils.AjaxResult
import com.tang.commons.utils.page.PageUtils
import com.tang.commons.utils.page.PageResult

/**
 * 聊天列表逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/app/chat/chat-list")
class AppChatListController(private val appChatListService: AppChatListService) {

    /**
     * 查询聊天列表列表
     *
     * @param appChatList 聊天列表对象
     * @return 聊天列表列表
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:list')")
    @GetMapping("/list")
    fun list(appChatList: AppChatList): PageResult {
        PageUtils.startPage()
        val list: List<AppChatList> = appChatListService.selectAppChatListList(appChatList)
        return PageUtils.getDataTable(list)
    }

    /**
     * 查询当前用户聊天列表列表
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:list')")
    @GetMapping("/list-all")
    fun listAll(): AjaxResult {
        val list = appChatListService.selectAppChatListListAll()
        return AjaxResult.success(list)
    }

    /**
     * 通过聊天列表主键查询聊天列表信息
     *
     * @param chatListId 聊天列表主键
     * @return 聊天列表信息
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:list')")
    @GetMapping("/{chatListId}")
    fun selectAppChatListByChatListId(@PathVariable chatListId: Long): AjaxResult {
        return AjaxResult.success(appChatListService.selectAppChatListByChatListId(chatListId))
    }

    /**
     * 新增聊天列表信息
     *
     * @param appChatList 聊天列表信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:add')")
    @PostMapping
    fun add(@RequestBody appChatList: AppChatList): AjaxResult {
        return AjaxResult.rows(appChatListService.insertAppChatList(appChatList))
    }

    /**
     * 通过聊天列主键修改聊天列表信息
     *
     * @param appChatList 聊天列表信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:edit')")
    @PutMapping
    fun edit(@RequestBody appChatList: AppChatList): AjaxResult {
        return AjaxResult.rows(appChatListService.updateAppChatListByChatListId(appChatList))
    }

    /**
     * 置顶聊天列表
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:edit')")
    @PutMapping("/stick/{chatListId}")
    fun stick(@PathVariable chatListId: Long): AjaxResult {
        return AjaxResult.rows(appChatListService.stickByChatListId(chatListId))
    }

    /**
     * 取消置顶聊天列表
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:edit')")
    @PutMapping("/unstick/{chatListId}")
    fun unstick(@PathVariable chatListId: Long): AjaxResult {
        return AjaxResult.rows(appChatListService.unstickByChatListId(chatListId))
    }

    /**
     * 通过聊天列表主键删除聊天列表信息
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:delete')")
    @DeleteMapping("/{chatListId}")
    fun delete(@PathVariable chatListId: Long): AjaxResult {
        return AjaxResult.rows(appChatListService.deleteAppChatListByChatListId(chatListId))
    }

    /**
     * 通过聊天列表主键数组批量删除聊天列表信息
     *
     * @param chatListIds 聊天列表主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:chat-list:delete')")
    @DeleteMapping
    fun deletes(@RequestBody chatListIds: Array<Long>): AjaxResult {
        return AjaxResult.rows(appChatListService.deleteAppChatListByChatListIds(chatListIds))
    }

}
